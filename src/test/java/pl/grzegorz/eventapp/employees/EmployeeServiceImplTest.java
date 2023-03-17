package pl.grzegorz.eventapp.employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDate.parse;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    private List<EmployeeOutputDto> allEmployees;
    private List<EmployeeOutputDto> allHiredEmployees;
    private EmployeeEntity employeeEntity;
    private EmployeeDto employeeDto;
    private final long employeeId = 1;
    private final long employeeNotFoundId = 6;

    @BeforeEach
    void setup() {
        allEmployees = getAllEmployeeDtoOutputList();
        allHiredEmployees = getAllHiredEmployees();
        employeeEntity = getFirstEmployeeEntity();
        employeeDto = getEmployeeDto();
    }

    @Test
    void shouldReturnListOfEmployeeOutputDtoObjects() {
//        given
        when(employeeRepository.findAllBy()).thenReturn(allEmployees);
//        when
        List<EmployeeOutputDto> listOfAllEmployees = employeeService.getAllEmployees();
//        then
        assertAll(
                () -> assertNotNull(listOfAllEmployees),
                () -> assertEquals(4, listOfAllEmployees.size()),
                () -> assertEquals(1L, listOfAllEmployees.get(0).getId()),
                () -> assertEquals("Tomasz", listOfAllEmployees.get(0).getName()),
                () -> assertEquals("Tomaszewski", listOfAllEmployees.get(0).getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", listOfAllEmployees.get(0).getEmail()),
                () -> assertEquals("DEVELOPER", listOfAllEmployees.get(0).getDepartment()),
                () -> assertEquals(parse("2022-01-01"), listOfAllEmployees.get(0).getDateOfStartingWork()),
                () -> assertNull(listOfAllEmployees.get(0).getDateOfEndingWork()),
                () -> assertEquals(TRUE, listOfAllEmployees.get(0).getIsEmployed()),
                () -> assertEquals(2L, listOfAllEmployees.get(1).getId()),
                () -> assertEquals("Łukasz", listOfAllEmployees.get(1).getName()),
                () -> assertEquals("Łukaszewski", listOfAllEmployees.get(1).getSurname()),
                () -> assertEquals("lukasz@lukaszewski.pl", listOfAllEmployees.get(1).getEmail()),
                () -> assertEquals("QA", listOfAllEmployees.get(1).getDepartment()),
                () -> assertEquals(parse("2022-03-01"), listOfAllEmployees.get(1).getDateOfStartingWork()),
                () -> assertEquals(parse("2022-09-30"), listOfAllEmployees.get(1).getDateOfEndingWork()),
                () -> assertEquals(FALSE, listOfAllEmployees.get(1).getIsEmployed()),
                () -> assertEquals(3L, listOfAllEmployees.get(2).getId()),
                () -> assertEquals("Bartosz", listOfAllEmployees.get(2).getName()),
                () -> assertEquals("Bartoszewski", listOfAllEmployees.get(2).getSurname()),
                () -> assertEquals("bartosz@bartoszewski.pl", listOfAllEmployees.get(2).getEmail()),
                () -> assertEquals("DEVOPS", listOfAllEmployees.get(2).getDepartment()),
                () -> assertEquals(parse("2022-05-01"), listOfAllEmployees.get(2).getDateOfStartingWork()),
                () -> assertNull(listOfAllEmployees.get(2).getDateOfEndingWork()),
                () -> assertEquals(TRUE, listOfAllEmployees.get(2).getIsEmployed()),
                () -> assertEquals(4L, listOfAllEmployees.get(3).getId()),
                () -> assertEquals("Michał", listOfAllEmployees.get(3).getName()),
                () -> assertEquals("Michałowski", listOfAllEmployees.get(3).getSurname()),
                () -> assertEquals("michal@michalowski.pl", listOfAllEmployees.get(3).getEmail()),
                () -> assertEquals("PROJECT-MANAGER", listOfAllEmployees.get(3).getDepartment()),
                () -> assertEquals(parse("2022-05-01"), listOfAllEmployees.get(3).getDateOfStartingWork()),
                () -> assertEquals(parse("2022-11-30"), listOfAllEmployees.get(3).getDateOfEndingWork()),
                () -> assertEquals(FALSE, listOfAllEmployees.get(3).getIsEmployed())
        );
    }

    @Test
    void shouldReturnListOfAllHiredEmployees() {
//        given
        when(employeeService.getAllHiredEmployees()).thenReturn(allHiredEmployees);
//        when
        List<EmployeeOutputDto> listOfAllHiredEmployees = employeeService.getAllHiredEmployees();
//        then
        assertAll(
                () -> assertNotNull(listOfAllHiredEmployees),
                () -> assertEquals(2, listOfAllHiredEmployees.size()),
                () -> assertEquals(1L, listOfAllHiredEmployees.get(0).getId()),
                () -> assertEquals("Tomasz", listOfAllHiredEmployees.get(0).getName()),
                () -> assertEquals("Tomaszewski", listOfAllHiredEmployees.get(0).getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", listOfAllHiredEmployees.get(0).getEmail()),
                () -> assertEquals("DEVELOPER", listOfAllHiredEmployees.get(0).getDepartment()),
                () -> assertEquals(parse("2022-01-01"), listOfAllHiredEmployees.get(0).getDateOfStartingWork()),
                () -> assertNull(listOfAllHiredEmployees.get(0).getDateOfEndingWork()),
                () -> assertEquals(TRUE, listOfAllHiredEmployees.get(0).getIsEmployed()),
                () -> assertEquals(3L, listOfAllHiredEmployees.get(1).getId()),
                () -> assertEquals("Bartosz", listOfAllHiredEmployees.get(1).getName()),
                () -> assertEquals("Bartoszewski", listOfAllHiredEmployees.get(1).getSurname()),
                () -> assertEquals("bartosz@bartoszewski.pl", listOfAllHiredEmployees.get(1).getEmail()),
                () -> assertEquals("DEVOPS", listOfAllHiredEmployees.get(1).getDepartment()),
                () -> assertEquals(parse("2022-05-01"), listOfAllHiredEmployees.get(1).getDateOfStartingWork()),
                () -> assertNull(listOfAllHiredEmployees.get(1).getDateOfEndingWork()),
                () -> assertEquals(TRUE, listOfAllHiredEmployees.get(1).getIsEmployed())
        );
    }

    @Test
    void shouldReturnEmployeeOutputDtoByIdWhenEntityWillBeExistsInTheDatabase() {
//        given
        when(employeeRepository.findAllById(employeeId)).thenReturn(of(allEmployees.get(0)));
//        when
        EmployeeOutputDto employeeById = employeeService.getEmployeeById(employeeId);
//        then
        assertAll(
                () -> assertNotNull(employeeById),
                () -> assertEquals(1L, employeeById.getId()),
                () -> assertEquals("Tomasz", employeeById.getName()),
                () -> assertEquals("Tomaszewski", employeeById.getSurname()),
                () -> assertEquals("tomasz@tomaszewski.pl", employeeById.getEmail()),
                () -> assertEquals("DEVELOPER", employeeById.getDepartment()),
                () -> assertEquals(parse("2022-01-01"), employeeById.getDateOfStartingWork()),
                () -> assertNull(employeeById.getDateOfEndingWork()),
                () -> assertEquals(TRUE, employeeById.getIsEmployed())
        );
    }

    @Test
    void shouldThrowExceptionWhenEntityWillBeNotExistsInTheDatabase() {
//        given
//        when + then
        try {
            assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
        } catch (EntityNotFoundException e) {
            assertEquals("Employee not found", e.getMessage());
        }
    }

    @Test
    void shouldReturnEmployeeSimpleEntityObject() {
//        given
        when(employeeRepository.findById(employeeId)).thenReturn(of(employeeEntity));
//        when
        EmployeeSimpleEntity employeeSimpleEntityById = employeeService.getEmployeeSimpleEntityById(employeeId);
        assertAll(
                () -> assertNotNull(employeeSimpleEntityById),
                () -> assertInstanceOf(EmployeeSimpleEntity.class, employeeSimpleEntityById)
        );
    }

    @Test
    void shouldCallTheSaveMethodFromEmployeeRepositoryInterfaceWhenCallCreateEmployeeMethod() {
//        given
//        when
        employeeService.createEmployee(employeeDto);
//        then
        verify(employeeRepository).save(any(EmployeeEntity.class));
    }

    @Test
    void shouldCallTheSaveMethodFromEmployeeRepositoryInterfaceWhenCallEditEmployeeMethod() {
//        given
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
//        when
        employeeService.editEmployee(employeeId, employeeDto);
//        then
        verify(employeeRepository).save(any(EmployeeEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenEmployeeEntityObjectWillBeNotExistsInTheDatabase() {
//        given
//        when + then
        try {
            assertThrows(EntityNotFoundException.class,
                    () -> employeeService.editEmployee(employeeNotFoundId, employeeDto));
        } catch (EntityNotFoundException e) {
            assertEquals("Employee not found", e.getMessage());
        }
    }

    @Test
    void shouldCallTheDeleteByIdMethodFromTheEmployeeRepositoryInterface() {
//        given
//        when
        employeeService.removeEmployee(employeeId);
//        then
        verify(employeeRepository).deleteById(employeeId);
    }
}