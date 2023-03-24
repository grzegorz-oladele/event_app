package pl.grzegorz.eventapp.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.eventapp.employees.EmployeeService;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.exceptions.OrganizerException;
import pl.grzegorz.eventapp.exceptions.ParticipantException;
import pl.grzegorz.eventapp.organizers.OrganizerService;
import pl.grzegorz.eventapp.organizers.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantService;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getFourthEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getSecondEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.*;
import static pl.grzegorz.eventapp.organizers.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizers.EventRole.MAIN_ORGANIZER;
import static pl.grzegorz.eventapp.organizers.OrganizerTestInitValue.getOrganizerSimpleEntity;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private OrganizerService organizerService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private ParticipantService participantService;

    private EventOutputDto eventOutputDto;
    private EventDto eventDto;
    private EventEntity eventEntity;
    private EmployeeSimpleEntity employeeSimpleEntity;
    private EmployeeSimpleEntity fourthEmployeeSimpleEntity;
    private EventSimpleEntity eventSimpleEntity;
    private OrganizerSimpleEntity organizerSimpleEntity;
    private EventEntity eventEntityWithFullParticipants;
    private final long eventId = 1;
    private final long employeeId = 2;
    private final long thirdEmployeeId = 3;
    private final long fourthEmployeeId = 4;
    private final long mainOrganizerId = 1;
    private final long wrongMainOrganizerId = 8;

    @BeforeEach
    void setup() {
        eventOutputDto = getEventOutputDto();
        eventDto = getEventDto();
        eventEntity = getEventEntity();
        eventSimpleEntity = getEventSimpleEntity();
        eventEntityWithFullParticipants = getEventEntityWithFullParticipants();
        employeeSimpleEntity = getSecondEmployeeSimpleEntity();
        fourthEmployeeSimpleEntity = getFourthEmployeeSimpleEntity();
        organizerSimpleEntity = getOrganizerSimpleEntity();
    }

    @Test
    void shouldReturnListOfEvents() {
//        given
        when(eventRepository.findAllBy()).thenReturn(Collections.singletonList(eventOutputDto));
//        when
        List<EventOutputDto> allEvents = eventService.getAllEvents();
//        then
        assertAll(
                () -> assertNotNull(allEvents),
                () -> assertEquals(1, allEvents.size()),
                () -> assertEquals(1, allEvents.get(0).getId()),
                () -> assertEquals("Gildia Java", allEvents.get(0).getEventName()),
                () -> assertEquals("2023-03-20T10:00", allEvents.get(0).getStartEventTime().toString()),
                () -> assertEquals("2023-03-20T12:00", allEvents.get(0).getEndEventTime().toString()),
                () -> assertEquals(2, allEvents.get(0).getOrganizers().size()),
                () -> assertEquals(1, allEvents.get(0).getOrganizers().get(0).getEmployee().getId()),
                () -> assertEquals("Tomasz", allEvents.get(0).getOrganizers().get(0).getEmployee().getName()),
                () -> assertEquals("Tomaszewski", allEvents.get(0).getOrganizers().get(0).getEmployee().getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", allEvents.get(0).getOrganizers().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", allEvents.get(0).getOrganizers().get(0).getEmployee().getDepartment()),
                () -> assertEquals(MAIN_ORGANIZER, allEvents.get(0).getOrganizers().get(0).getRole()),
                () -> assertEquals(2, allEvents.get(0).getOrganizers().get(1).getEmployee().getId()),
                () -> assertEquals("Paweł", allEvents.get(0).getOrganizers().get(1).getEmployee().getName()),
                () -> assertEquals("Pawłowski", allEvents.get(0).getOrganizers().get(1).getEmployee().getSurname()),
                () -> assertEquals("pawel@pawlowski.pl", allEvents.get(0).getOrganizers().get(1).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", allEvents.get(0).getOrganizers().get(1).getEmployee().getDepartment()),
                () -> assertEquals(ASSISTANT, allEvents.get(0).getOrganizers().get(1).getRole()),
                () -> assertEquals(2, allEvents.get(0).getParticipants().size()),
                () -> assertEquals(3, allEvents.get(0).getParticipants().get(0).getEmployee().getId()),
                () -> assertEquals("Bartosz", allEvents.get(0).getParticipants().get(0).getEmployee().getName()),
                () -> assertEquals("Bartoszewski", allEvents.get(0).getParticipants().get(0).getEmployee().getSurname()),
                () -> assertEquals("bartosz@bartoszewski.pl", allEvents.get(0).getParticipants().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVOPS", allEvents.get(0).getParticipants().get(0).getEmployee().getDepartment()),
                () -> assertEquals(4, allEvents.get(0).getParticipants().get(1).getEmployee().getId()),
                () -> assertEquals("Michał", allEvents.get(0).getParticipants().get(1).getEmployee().getName()),
                () -> assertEquals("Michałowski", allEvents.get(0).getParticipants().get(1).getEmployee().getSurname()),
                () -> assertEquals("michal@michalowski.pl", allEvents.get(0).getParticipants().get(1).getEmployee().getEmail()),
                () -> assertEquals("PROJECT-MANAGER", allEvents.get(0).getParticipants().get(1).getEmployee().getDepartment())
        );
    }

    @Test
    void shouldThrowExceptionWhenEventEntityWillBeNotExistsInTheDatabase() {
//        given
        when(eventRepository.findAllById(eventId)).thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class, () -> eventService.getEventById(eventId));
        } catch (EntityNotFoundException e) {
            assertEquals("Event not found", e.getMessage());
        }
    }

    @Test
    void shouldReturnEventOutputDtoObjectWhenEventEntityWillBeExistsInTheDatabase() {
//        given
        when(eventRepository.findAllById(eventId)).thenReturn(of(eventOutputDto));
//        when
        EventOutputDto eventById = eventService.getEventById(eventId);
//        then
        assertAll(
                () -> assertNotNull(eventById),
                () -> assertEquals("Gildia Java", eventById.getEventName()),
                () -> assertEquals("2023-03-20T10:00", eventById.getStartEventTime().toString()),
                () -> assertEquals("2023-03-20T12:00", eventById.getEndEventTime().toString()),
                () -> assertEquals(2, eventById.getOrganizers().size()),
                () -> assertEquals(1, eventById.getOrganizers().get(0).getEmployee().getId()),
                () -> assertEquals("Tomasz", eventById.getOrganizers().get(0).getEmployee().getName()),
                () -> assertEquals("Tomaszewski", eventById.getOrganizers().get(0).getEmployee().getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", eventById.getOrganizers().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", eventById.getOrganizers().get(0).getEmployee().getDepartment()),
                () -> assertEquals(MAIN_ORGANIZER, eventById.getOrganizers().get(0).getRole()),
                () -> assertEquals(2, eventById.getOrganizers().get(1).getEmployee().getId()),
                () -> assertEquals("Paweł", eventById.getOrganizers().get(1).getEmployee().getName()),
                () -> assertEquals("Pawłowski", eventById.getOrganizers().get(1).getEmployee().getSurname()),
                () -> assertEquals("pawel@pawlowski.pl", eventById.getOrganizers().get(1).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", eventById.getOrganizers().get(1).getEmployee().getDepartment()),
                () -> assertEquals(ASSISTANT, eventById.getOrganizers().get(1).getRole()),
                () -> assertEquals(2, eventById.getParticipants().size()),
                () -> assertEquals(3, eventById.getParticipants().get(0).getEmployee().getId()),
                () -> assertEquals("Bartosz", eventById.getParticipants().get(0).getEmployee().getName()),
                () -> assertEquals("Bartoszewski", eventById.getParticipants().get(0).getEmployee().getSurname()),
                () -> assertEquals("bartosz@bartoszewski.pl", eventById.getParticipants().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVOPS", eventById.getParticipants().get(0).getEmployee().getDepartment()),
                () -> assertEquals(4, eventById.getParticipants().get(1).getEmployee().getId()),
                () -> assertEquals("Michał", eventById.getParticipants().get(1).getEmployee().getName()),
                () -> assertEquals("Michałowski", eventById.getParticipants().get(1).getEmployee().getSurname()),
                () -> assertEquals("michal@michalowski.pl", eventById.getParticipants().get(1).getEmployee().getEmail()),
                () -> assertEquals("PROJECT-MANAGER", eventById.getParticipants().get(1).getEmployee().getDepartment())
        );
    }

    @Test
    void shouldCallSaveMethodFromTheEventRepositoryInterfaceWhenUserWillBeWantAddNewEventToTheDatabase() {
//        given
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);
        when(employeeService.getEmployeeSimpleEntityById(employeeId)).thenReturn(employeeSimpleEntity);
        when(organizerService.createOrganizer(employeeSimpleEntity, eventSimpleEntity, MAIN_ORGANIZER))
                .thenReturn(organizerSimpleEntity);
//        when
        eventService.createEvent(employeeId, eventDto);
//        then
        verify(eventRepository).save(eventEntity);
    }

    @Test
    void shouldCallSaveMethodFromTheEventRepositoryInterfaceWhenUserWillBeWantEditEventFormTheDatabase() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
//        when
        eventService.editEventById(mainOrganizerId, eventId, eventDto);
//        then
        verify(eventRepository).save(any(EventEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenEventWillBeNotExistsInTheDatabase() {
//        given
        when(eventRepository.findById(eventId)).thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> eventService.editEventById(employeeId, eventId, eventDto));
        } catch (EntityNotFoundException e) {
            assertEquals("Event not found", e.getMessage());
        }
    }

    @Test
    void shouldCallSaveMethodFromEventRepositoryInterfaceWhenEventEntityAndEmployeeEntityWillBeExistsInTheDatabase() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
        when(employeeService.getEmployeeSimpleEntityById(fourthEmployeeId)).thenReturn(fourthEmployeeSimpleEntity);
        when(organizerService.createOrganizer(fourthEmployeeSimpleEntity, eventSimpleEntity, ASSISTANT))
                .thenReturn(organizerSimpleEntity);
//        when
        eventService.addEmployeeAsOrganizer(mainOrganizerId, fourthEmployeeId, eventId);
//        then
        verify(eventRepository).save(eventEntity);
    }

    @Test
    void shouldThrowExceptionWhenCall_AddEmployeeAsOrganizer_And_EventWillBeNotExistsInTheDatabase() {
//        given
        when(eventRepository.findById(eventId)).thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> eventService.addEmployeeAsOrganizer(mainOrganizerId, employeeId, eventId));
        } catch (EntityNotFoundException e) {
            assertEquals("Event not found", e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenEventWillBeNotExistsWhenCallRemoveOrganizerFromEventMethod() {
//        given
        when(eventRepository.findById(eventId)).thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> eventService.removeOrganizerFromEvent(mainOrganizerId, employeeId, eventId));
        } catch (EntityNotFoundException e) {
            assertEquals("Event not found", e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenMainOrganizerIdValueWillBeNotCorrect() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
//        when + then
        try {
            assertThrows(OrganizerException.class,
                    () -> eventService.removeOrganizerFromEvent(wrongMainOrganizerId, employeeId, eventId));
        } catch (OrganizerException e) {
            assertEquals("You dont have permission to remove organizer from an event", e.getMessage());
        }
    }

    @Test
    void shouldCall_RemoveByEventAndEmployee_MethodFromOrganizerService() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
//        when
        eventService.removeOrganizerFromEvent(mainOrganizerId, employeeId, eventId);
//        then
        verify(organizerService).removeByEventAndEmployee(eventId, employeeId);
        verify(eventRepository).save(eventEntity);
    }

    @Test
    void shouldThrowExceptionIn_addEmployeeAsParticipant_MethodWhenEventEntityWillBeNotExistsInTheDatabase() {
//        given
        when(eventRepository.findById(eventId)).thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> eventService.addEmployeeAsParticipant(eventId, employeeId));
        } catch (EntityNotFoundException e) {
            assertEquals("Event not found", e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhen_CurrentParticipantNumber_WillBeFull() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntityWithFullParticipants));
//        when + then
        try {
            assertThrows(ParticipantException.class,
                    () -> eventService.addEmployeeAsParticipant(eventId, thirdEmployeeId));
        } catch (ParticipantException e) {
            assertEquals("You can't sign up for an event. The number of participant has already been reached",
                    e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhen_EmployeeWillBeExistsInEventAsOrganizer() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
//        when + then
        try {
            assertThrows(OrganizerException.class, () -> eventService.addEmployeeAsParticipant(eventId, employeeId));
        } catch (ParticipantException e) {
            assertEquals("Organizer already exists in an event as organizer", e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhen_EmployeeWilBeExistsInEventAsParticipant() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
//        when + then
        try {
            assertThrows(ParticipantException.class,
                    () -> eventService.addEmployeeAsParticipant(eventId, thirdEmployeeId));
        } catch (ParticipantException e) {
            assertEquals("Employee already exists in an event as participant", e.getMessage());
        }
    }

    @Test
    void shouldCall_Save_Method_From_EventRepository_Interface() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
        when(employeeService.getEmployeeSimpleEntityById(fourthEmployeeId)).thenReturn(fourthEmployeeSimpleEntity);
//        when
        eventService.addEmployeeAsParticipant(eventId, fourthEmployeeId);
//        then
        verify(eventRepository).save(eventEntity);
        assertEquals(1, eventEntity.getCurrentParticipantsNumber());
    }

    @Test
    void shouldThrowExceptionIn_removeParticipantFromEvent_MethodWhenEventEntityWillBeNotExistsInTheDatabase() {
//        given
        when(eventRepository.findById(eventId)).thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> eventService.removeParticipantFromEvent(eventId, employeeId));
        } catch (EntityNotFoundException e) {
            assertEquals("Event not found", e.getMessage());
        }
    }

    @Test
    void shouldCall_RemoveByEventAndEmployee_From_ParticipantService_Interface() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntityWithFullParticipants));
//        when
        eventService.removeParticipantFromEvent(eventId, thirdEmployeeId);
//        then
        verify(participantService).removeByEventAndEmployee(eventId, thirdEmployeeId);
    }
}