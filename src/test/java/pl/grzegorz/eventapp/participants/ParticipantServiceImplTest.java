package pl.grzegorz.eventapp.participants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import javax.persistence.EntityNotFoundException;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.getEventSimpleEntity;
import static pl.grzegorz.eventapp.participants.ParticipantTestInitValue.getParticipantEntity;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {

    @InjectMocks
    private ParticipantServiceImpl participantService;
    @Mock
    private ParticipantRepository participantRepository;

    private ParticipantEntity participantEntity;
    private EmployeeSimpleEntity employeeSimpleEntity;
    private EventSimpleEntity eventSimpleEntity;
    private final long eventId = 1;
    private final long employeeId = 2;

    @BeforeEach
    void setup() {
        participantEntity = getParticipantEntity();
        employeeSimpleEntity = getEmployeeSimpleEntity();
        eventSimpleEntity = getEventSimpleEntity();
    }

    @Test
    void shouldReturnReturnParticipantSimpleEntityObject() {
//        given
        when(participantRepository.save(any(ParticipantEntity.class))).thenReturn(participantEntity);
//        when
        ParticipantSimpleEntity participant =
                participantService.createParticipant(employeeSimpleEntity, eventSimpleEntity);
//        then
        assertAll(
                () -> assertNotNull(participant),
                () -> assertEquals(1L, participant.getEmployee().getId()),
                () -> assertEquals("Tomasz", participant.getEmployee().getName()),
                () -> assertEquals("Tomaszewski", participant.getEmployee().getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", participant.getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", participant.getEmployee().getDepartment())
        );
        verify(participantRepository).save(any(ParticipantEntity.class));
    }

    @Test
    void shouldCallDeleteMethodFromParticipantRepositoryInterface() {
//        given
        when(participantRepository.findByEmployee_IdAndEvent_Id(employeeId, eventId)).thenReturn(of(participantEntity));
//        when
        participantService.removeByEventAndEmployee(eventId, employeeId);
//        then
        verify(participantRepository).delete(participantEntity);
    }

    @Test
    void shouldThrowExceptionWhenParticipantEntityObjectWillBeNotExistsInTheDatabase() {
//        given
        when(participantRepository.findByEmployee_IdAndEvent_Id(employeeId, eventId))
                .thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> participantService.removeByEventAndEmployee(eventId, employeeId));
        } catch (EntityNotFoundException e) {
            assertEquals("Participant not found", e.getMessage());
        }
    }
}