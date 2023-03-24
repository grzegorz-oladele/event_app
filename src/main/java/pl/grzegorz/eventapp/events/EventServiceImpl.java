package pl.grzegorz.eventapp.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grzegorz.eventapp.employees.EmployeeService;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.exceptions.OrganizerException;
import pl.grzegorz.eventapp.exceptions.ParticipantException;
import pl.grzegorz.eventapp.organizers.OrganizerService;
import pl.grzegorz.eventapp.organizers.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantService;
import pl.grzegorz.eventapp.participants.ParticipantSimpleEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.List;

import static pl.grzegorz.eventapp.events.EventEntity.toEntity;
import static pl.grzegorz.eventapp.events.EventSimpleEntity.*;
import static pl.grzegorz.eventapp.organizers.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizers.EventRole.MAIN_ORGANIZER;

@Service
@RequiredArgsConstructor
@Slf4j
class EventServiceImpl implements EventService {

    private static final String EVENT_NOT_FOUND_MESSAGE = "Event not found";

    private final EventRepository eventRepository;
    private final OrganizerService organizerService;
    private final EmployeeService employeeService;
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
                .orElseThrow(() -> {
                    log.error("Event with id -> {} not found", eventId);
                    throw new EntityNotFoundException(EVENT_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    @Transactional
    public void createEvent(long employeeId, EventDto eventDto) {
        EventEntity event = toEntity(eventDto);
        EventEntity savedEvent = eventRepository.save(event);
        log.info("Create new event with id -> {}", savedEvent.getId());
        EmployeeSimpleEntity employeeSimple = employeeService.getEmployeeSimpleEntityById(employeeId);
        EventSimpleEntity eventSimple = toSimpleEntity(savedEvent);
        OrganizerSimpleEntity organizerSimple = organizerService.createOrganizer(employeeSimple, eventSimple, MAIN_ORGANIZER);
        savedEvent.getOrganizers().add(organizerSimple);
        eventRepository.save(savedEvent);
    }

    @Override
    public void editEventById(long mainOrganizerId, long eventId, EventDto eventDto) {
        EventEntity event = getEventEntityById(eventId);
        List<OrganizerSimpleEntity> organizers = event.getOrganizers();
        checkMainOrganizerIsCorrect(mainOrganizerId, organizers);
        List<ParticipantSimpleEntity> participants = event.getParticipants();
        EventEntity editedEventEntity = toEntity(eventDto);
        editedEventEntity.getParticipants().addAll(participants);
        editedEventEntity.getOrganizers().addAll(organizers);
        editedEventEntity.setId(eventId);
        eventRepository.save(editedEventEntity);
        log.info("Save edited event with id -> {}", eventId);
    }

    @Override
    @Transactional
    public void addEmployeeAsOrganizer(long mainOrganizerId, long employeeId, long eventId) {
        EventEntity event = getEventEntityById(eventId);
        checkEmployeeExistsInAnEventAsOrganizer(employeeId, event.getOrganizers());
        checkEmployeeExistsInAnEventAsParticipant(employeeId, event.getParticipants());
        EmployeeSimpleEntity employeeSimple = employeeService.getEmployeeSimpleEntityById(employeeId);
        EventSimpleEntity eventSimple = toSimpleEntity(event);
        OrganizerSimpleEntity organizerSimple = organizerService.createOrganizer(employeeSimple, eventSimple, ASSISTANT);
        event.getOrganizers().add(organizerSimple);
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void removeOrganizerFromEvent(long mainOrganizerId, long employeeId, long eventId) {
        EventEntity event = getEventEntityById(eventId);
        List<OrganizerSimpleEntity> organizers = event.getOrganizers();
        checkMainOrganizerIsCorrect(mainOrganizerId, organizers);
        Iterator<OrganizerSimpleEntity> iterator = event.getOrganizers().iterator();
        while (iterator.hasNext()) {
            OrganizerSimpleEntity organizer = iterator.next();
            if (organizer.getEmployee().getId() == employeeId) {
                iterator.remove();
                organizerService.removeByEventAndEmployee(eventId, employeeId);
                event.setCurrentParticipantsNumber(event.getCurrentParticipantsNumber() - 1);
                eventRepository.save(event);
            }
        }
    }


    @Override
    @Transactional
    public void addEmployeeAsParticipant(long eventId, long employeeId) {
        EventEntity event = getEventEntityById(eventId);
        checkCurrentParticipantNumberAndThrowExceptionIfItIsReached(event);
        checkEmployeeExistsInAnEventAsOrganizer(employeeId, event.getOrganizers());
        checkEmployeeExistsInAnEventAsParticipant(employeeId, event.getParticipants());
        EmployeeSimpleEntity employeeSimple = employeeService.getEmployeeSimpleEntityById(employeeId);
        EventSimpleEntity eventSimple = toSimpleEntity(event);
        ParticipantSimpleEntity participantSimple = participantService.createParticipant(employeeSimple, eventSimple);
        event.getParticipants().add(participantSimple);
        event.setCurrentParticipantsNumber(event.getCurrentParticipantsNumber() + 1);
        eventRepository.save(event);
    }

    private void checkCurrentParticipantNumberAndThrowExceptionIfItIsReached(EventEntity event) {
        if (event.getLimitOfParticipants() == (event.getCurrentParticipantsNumber())) {
            log.error("You can't sign up for an event. The number of participant has already been reached");
            throw new ParticipantException("You can't sign up for an event. " +
                    "The number of participant has already been reached");
        }
    }

    @Override
    @Transactional
    public void removeParticipantFromEvent(long eventId, long employeeId) {
        EventEntity event = getEventEntityById(eventId);
        Iterator<ParticipantSimpleEntity> iterator = event.getParticipants().iterator();
        while (iterator.hasNext()) {
            ParticipantSimpleEntity participant = iterator.next();
            if (participant.getEmployee().getId() == employeeId) {
                iterator.remove();
                participantService.removeByEventAndEmployee(eventId, employeeId);
            }
        }
    }

    private void checkEmployeeExistsInAnEventAsParticipant(long employeeId, List<ParticipantSimpleEntity> participants) {
        participants.forEach(participant -> {
            if (participant.getEmployee().getId() == employeeId) {
                throw new ParticipantException("Employee already exists in an event as participant");
            }
        });
    }

    private void checkEmployeeExistsInAnEventAsOrganizer(long employeeId, List<OrganizerSimpleEntity> organizers) {
        organizers.forEach(organizer -> {
            if (organizer.getEmployee().getId() == employeeId) {
                throw new OrganizerException("Organizer already exists in an event as organizer");
            }
        });
    }

    private void checkMainOrganizerIsCorrect(long mainOrganizerId, List<OrganizerSimpleEntity> organizers) {
        List<OrganizerSimpleEntity> mainOrganizers = organizers
                .stream()
                .filter(organizer -> organizer.getEmployee().getId() == mainOrganizerId)
                .toList();
        if (mainOrganizers.isEmpty()) {
            throw new OrganizerException("You dont have permission to remove organizer from an event");
        }
    }

    private EventEntity getEventEntityById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Event with id -> {} not found", eventId);
                    throw new EntityNotFoundException(EVENT_NOT_FOUND_MESSAGE);
                });
    }
}