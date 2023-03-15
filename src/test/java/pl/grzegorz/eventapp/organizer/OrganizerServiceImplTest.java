package pl.grzegorz.eventapp.organizer;

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
import static pl.grzegorz.eventapp.organizer.EventRole.MAIN_ORGANIZER;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getOrganizerEntity;

@ExtendWith(MockitoExtension.class)
class OrganizerServiceImplTest {

    @InjectMocks
    private OrganizerServiceImpl organizerService;
    @Mock
    private OrganizerRepository organizerRepository;

    private OrganizerEntity organizerEntity;
    private EmployeeSimpleEntity employeeSimpleEntity;
    private EventSimpleEntity eventSimpleEntity;
    private final long eventId = 1;
    private final long employeeId = 2;

    @BeforeEach
    void setup() {
        organizerEntity = getOrganizerEntity();
        employeeSimpleEntity = getEmployeeSimpleEntity();
        eventSimpleEntity = getEventSimpleEntity();
    }

    @Test
    void shouldReturnOrganizerSimpleEntityObject() {
//        given
        when(organizerRepository.save(any(OrganizerEntity.class))).thenReturn(organizerEntity);
//        when
        OrganizerSimpleEntity organizer =
                organizerService.createOrganizer(employeeSimpleEntity, eventSimpleEntity, MAIN_ORGANIZER);
//        then
        assertAll(
                () -> assertNotNull(organizer),
                () -> assertEquals(1L, organizer.getEmployee().getId()),
                () -> assertEquals("Tomasz", organizer.getEmployee().getName()),
                () -> assertEquals("Tomaszewski", organizer.getEmployee().getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", organizer.getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", organizer.getEmployee().getDepartment())
        );
        verify(organizerRepository).save(any(OrganizerEntity.class));
    }

    @Test
    void shouldCallDeleteMethodFromOrganizerRepositoryInterfaceEmployeeEntityObjectWillBeExistsInTheDatabase() {
//        given
        when(organizerRepository.findByEmployee_IdAndEvent_Id(employeeId, eventId)).thenReturn(of(organizerEntity));
//        when
        organizerService.removeByEventAndEmployee(eventId, employeeId);
//        then
        verify(organizerRepository).delete(organizerEntity);
    }

    @Test
    void shouldThrowExceptionWhenOrganizerEntityObjectWillBeNotExistsInTheDatabase() {
//        given
        when(organizerRepository.findByEmployee_IdAndEvent_Id(employeeId, eventId))
                .thenThrow(EntityNotFoundException.class);
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> organizerService.removeByEventAndEmployee(eventId, employeeId));
        } catch (EntityNotFoundException e) {
            assertEquals("Organizer not found", e.getMessage());
        }
    }
}