package pl.grzegorz.eventapp.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.email.message.MessageFactory;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.exceptions.EntityException;
import pl.grzegorz.eventapp.exceptions.ParticipantException;
import pl.grzegorz.eventapp.employees.EmployeeService;
import pl.grzegorz.eventapp.employees.dto.simple_entity.EmployeeSimpleEntity;

import java.util.List;

import static pl.grzegorz.eventapp.events.EventEntity.toEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class EventServiceImpl implements EventService {

    private static final String EVENT_NOT_FOUND_MESSAGE = "Event not found";
    private static final String EVENT_NOT_FOUND_LOG_ERROR_MESSAGE = "Event using id -> {} not found";

    private final EventRepository eventRepository;
    private final EmployeeService employeeService;

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
    public void createEvent(EventDto eventDto) {
        EventEntity event = toEntity(eventDto);
        eventRepository.save(event);
        log.info("Create new event with id -> {}", event.getId());
    }

    @Override
    public void editEventById(long eventId, EventDto eventDto) {
        List<EmployeeSimpleEntity> participants = getParticipantsByEventId(eventId);
        EventEntity editedEvent = toEntity(eventDto);
        editedEvent.setId(eventId);
        editedEvent.setParticipants(participants);
        eventRepository.save(editedEvent);
        log.info("Save edited event with id -> {}", eventId);
    }

    private List<EmployeeSimpleEntity> getParticipantsByEventId(long eventId) {
        return eventRepository.findAllEmployeesByEventId(eventId)
                .orElseThrow(() -> {
                    log.error(EVENT_NOT_FOUND_LOG_ERROR_MESSAGE, eventId);
                    throw new EntityException(EVENT_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public void addEmployeeToEvent(long eventId, long employeeId) {
        EmployeeSimpleEntity employeeSimpleEntity = employeeService.getWorkingEmployeeSimpleEntityById(employeeId);
        EventEntity event = getEventEntityById(eventId);
        checkListOfParticipantsSize(eventId, event);
        checkParticipantExistInEventAndThrowExceptionIfIs(eventId, employeeId, employeeSimpleEntity, event);
        event.getParticipants().add(employeeSimpleEntity);
        eventRepository.save(event);
        log.info("Add employee using id -> {} to event using id -> {}", employeeId, eventId);
    }

    private EventEntity getEventEntityById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> getEntityNotFoundException(eventId));
    }

    private void checkParticipantExistInEventAndThrowExceptionIfIs(long eventId, long employeeId, EmployeeSimpleEntity employeeSimpleEntity, EventEntity event) {
        if (event.getParticipants().contains(employeeSimpleEntity)) {
            log.error("Employee using id -> {} already exist in event using id -> {}", employeeId, eventId);
            throw new ParticipantException("Participant already exist in event");
        }
    }

    private void checkListOfParticipantsSize(long eventId, EventEntity event) {
        if (event.getParticipants().size() >= event.getLimitOfParticipants()) {
            throwParticipantsLimitExceptionException(eventId);
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