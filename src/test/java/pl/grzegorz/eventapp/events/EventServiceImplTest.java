package pl.grzegorz.eventapp.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.eventapp.employees.EmployeeService;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.organizer.EventRole;
import pl.grzegorz.eventapp.organizer.OrganizerService;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantService;

import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getSecondEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.getEventEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.getEventSimpleEntity;
import static pl.grzegorz.eventapp.organizer.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getAssistantOrganizerSimpleEntity;

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

    private EventEntity eventEntity;
    private EmployeeSimpleEntity employeeSimpleEntity;
    private EventSimpleEntity eventSimpleEntity;
    private OrganizerSimpleEntity organizerSimpleEntity;

    private final long eventId = 1;
    private final long employeeId = 2;

    @BeforeEach
    void setup() {
        eventEntity = getEventEntity();
        employeeSimpleEntity = getSecondEmployeeSimpleEntity();
        eventSimpleEntity = getEventSimpleEntity();
        organizerSimpleEntity = getAssistantOrganizerSimpleEntity();
    }

    @Test
    void test() {
//        given
        when(eventRepository.findById(eventId)).thenReturn(of(eventEntity));
        when(employeeService.getEmployeeSimpleEntityById(employeeId)).thenReturn(employeeSimpleEntity);
        when(organizerService.createOrganizer(employeeSimpleEntity, eventSimpleEntity, ASSISTANT))
                .thenReturn(organizerSimpleEntity);
//        when
        eventService.addEmployeeAsOrganizer(anyLong(), employeeId, eventId);
//        then
        verify(eventRepository).save(any(EventEntity.class));
    }
}