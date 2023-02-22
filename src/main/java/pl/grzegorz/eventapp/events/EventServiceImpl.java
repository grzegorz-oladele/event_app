package pl.grzegorz.eventapp.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grzegorz.eventapp.employees.EmployeeService;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.exceptions.EntityException;
import pl.grzegorz.eventapp.exceptions.ParticipantException;
import pl.grzegorz.eventapp.organizer.OrganizerService;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantService;
import pl.grzegorz.eventapp.participants.ParticipantSimpleEntity;

import java.util.List;

import static pl.grzegorz.eventapp.events.EventEntity.toEntity;
import static pl.grzegorz.eventapp.events.EventSimpleEntity.toSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class EventServiceImpl implements EventService {

    private static final String EVENT_NOT_FOUND_MESSAGE = "Event not found";
    private static final String EVENT_NOT_FOUND_LOG_ERROR_MESSAGE = "Event using id -> {} not found";

    private final EventRepository eventRepository;
    private final EmployeeService employeeService;
    private final OrganizerService organizerService;
    private final ParticipantService participantService;

    @Override
    public List<EventOutputDto> getAllEvents() {
        List<EventOutputDto> events = eventRepository.findAllBy();
        log.info("Return list of events counting {} records", events.size());
        return events;
    }

    @Override
    public EventOutputDto getEventById(long eventId) {
        return eventRepository.findAllById(eventId)
                .orElseThrow(() -> getEntityNotFoundException(eventId));
    }

    @Override
    public void createEvent(long employeeId, EventDto eventDto) {
        EventEntity event = toEntity(eventDto);
        OrganizerSimpleEntity organizerSimple = getOrganizerSimpleEntity(employeeId, event);
        event.getOrganizers().add(organizerSimple);
        eventRepository.save(event);
        log.info("Create new event with id -> {}", event.getId());
    }

    private OrganizerSimpleEntity getOrganizerSimpleEntity(long employeeId, EventEntity event) {
        EmployeeSimpleEntity employeeSimple = employeeService.getWorkingEmployeeSimpleEntityById(employeeId);
        EventSimpleEntity eventSimple = toSimpleEntity(event);
        return organizerService.createOrganizer(employeeSimple, eventSimple);
    }

    @Override
    public void editEventById(long mainOrganizerId, long eventId, EventDto eventDto) {
        EventEntity event = getEventEntityById(eventId);
        List<ParticipantSimpleEntity> participants = event.getParticipants();
        List<OrganizerSimpleEntity> organizers = event.getOrganizers();
        EventEntity editedEventEntity = toEntity(eventDto);
        editedEventEntity.getParticipants().addAll(participants);
        editedEventEntity.getOrganizers().addAll(organizers);
        eventRepository.save(editedEventEntity);
        log.info("Save edited event with id -> {}", eventId);
    }

    @Override
    @Transactional
    public void addEmployeeToEventAsOrganizer(long eventId, long mainOrganizerId, long employeeId) {
        EventEntity event = getEventEntityById(eventId);

        EmployeeSimpleEntity employeeSimple = employeeService.getWorkingEmployeeSimpleEntityById(employeeId);
        checkEmployeeExistInEventAndThrowExceptionIfIs(event, employeeSimple);
        EventSimpleEntity eventSimple = toSimpleEntity(event);
        OrganizerSimpleEntity organizerSimple = createOrganizer(employeeSimple, eventSimple);
        organizerSimple.setAssistantRole();
        event.getOrganizers().add(organizerSimple);
        eventRepository.save(event);
        log.info("Add employee {} {} to {} as organizer with role {}", employeeSimple.getName(),
                employeeSimple.getSurname(), eventSimple.getEventName(), organizerSimple.getRole());
    }

    private OrganizerSimpleEntity createOrganizer(EmployeeSimpleEntity employee, EventSimpleEntity event) {
        return organizerService.createOrganizer(employee, event);
    }

    @Override
    public void addEmployeeToEventAsParticipant(long eventId, long employeeId) {
        EventEntity event = getEventEntityById(eventId);
        checkListOfParticipantsSize(event);
        EmployeeSimpleEntity employeeSimple = employeeService.getWorkingEmployeeSimpleEntityById(employeeId);
        checkEmployeeExistInEventAndThrowExceptionIfIs(event,employeeSimple);
        EventSimpleEntity eventSimple = toSimpleEntity(event);
        ParticipantSimpleEntity participantSimple = participantService.createParticipant(employeeSimple, eventSimple);
        event.getParticipants().add(participantSimple);
        eventRepository.save(event);
        log.info("Add employee {} {} to {} as participant", employeeSimple.getName(), employeeSimple.getSurname(),
                eventSimple.getEventName());
    }

    private EventEntity getEventEntityById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> getEntityNotFoundException(eventId));
    }

    private void checkEmployeeExistInEventAndThrowExceptionIfIs(EventEntity event, EmployeeSimpleEntity employee) {
        checkEmployeeExistInEventAsParticipant(event, employee);
        checkEmployeeExistInEventAsOrganizer(event, employee);
    }

    private void checkEmployeeExistInEventAsParticipant(EventEntity event, EmployeeSimpleEntity employee) {
        if (isExistAsParticipant(event, employee)) {
            throw new ParticipantException("Employee is already exist in event as participant");
        }
    }

    private void checkEmployeeExistInEventAsOrganizer(EventEntity event, EmployeeSimpleEntity employee) {
        if (isExistAsOrganizer(event, employee)) {
            throw new ParticipantException("Employee is already exist in event as organizer");
        }
    }

    private boolean isExistAsOrganizer(EventEntity event, EmployeeSimpleEntity employee) {
        return event.getOrganizers()
                .stream()
                .map(OrganizerSimpleEntity::getId).toList()
                .contains(employee.getId());
    }

    private boolean isExistAsParticipant(EventEntity event, EmployeeSimpleEntity employee) {
        return event.getParticipants()
                .stream()
                .map(ParticipantSimpleEntity::getId).toList()
                .contains(employee.getId());
    }

    private void checkListOfParticipantsSize(EventEntity event) {
        if (event.getParticipants().size() >= event.getLimitOfParticipants()) {
            throwParticipantsLimitExceptionException(event.getId());
        }
    }

    private EntityException getEntityNotFoundException(long eventId) {
        throwEntityNotFoundException(eventId);
        return null;
    }

    private void throwEntityNotFoundException(long eventId) {
        log.error(EVENT_NOT_FOUND_LOG_ERROR_MESSAGE, eventId);
        throw new EntityException(EVENT_NOT_FOUND_MESSAGE);
    }

    private void throwParticipantsLimitExceptionException(long eventId) {
        log.error(EVENT_NOT_FOUND_LOG_ERROR_MESSAGE, eventId);
        throw new ParticipantException("Unable to sign up for the event. The limit of event participants has already " +
                "been reached");
    }
}