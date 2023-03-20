package pl.grzegorz.eventapp.employees;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import pl.grzegorz.eventapp.AbstractIntegrationTest;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeEndOfWorkDto;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.*;

public class EmployeeControllerTest extends AbstractIntegrationTest {

    private static final String URL_PATH = "/employees/";

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity firstEmployee;
    private EmployeeEntity secondEmployee;
    private EmployeeDto employeeDto;
    private EmployeeEndOfWorkDto employeeEndOfWorkDto;


    @Before
    public void setup() {
        firstEmployee = employeeRepository.save(getFirstEmployeeEntity());
        secondEmployee = employeeRepository.save(getSecondEmployeeEntity());
        employeeDto = getEmployeeDto();
        employeeEndOfWorkDto = getEmployeeEndOfWorkDto();
    }

    @AfterEach
    public void teardown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void shouldReturnListOfEmployees() throws Exception {
        mockMvc.perform(get(URL_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Tomasz")))
                .andExpect(jsonPath("$[0].surname", is("Tomaszewski")))
                .andExpect(jsonPath("$[0].email", is("tomasz@tomaszewski.pl")))
                .andExpect(jsonPath("$[0].department", is("DEVELOPER")))
                .andExpect(jsonPath("$[0].dateOfStartingWork", is("2022-01-01")))
                .andExpect(jsonPath("$[0].dateOfEndingWork").value(nullValue()))
                .andExpect(jsonPath("$[0].isEmployed", is(true)))
                .andExpect(jsonPath("$[1].name", is("Michał")))
                .andExpect(jsonPath("$[1].surname", is("Michałowski")))
                .andExpect(jsonPath("$[1].email", is("michal@michalowski.pl")))
                .andExpect(jsonPath("$[1].dateOfStartingWork", is("2022-05-01")))
                .andExpect(jsonPath("$[1].dateOfEndingWork").value(nullValue()))
                .andExpect(jsonPath("$[1].isEmployed", is(false)));
    }

    @Test
    public void shouldReturnListOfWorkingEmployees() throws Exception {
        mockMvc.perform(get(URL_PATH + "working"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Tomasz")))
                .andExpect(jsonPath("$[0].surname", is("Tomaszewski")))
                .andExpect(jsonPath("$[0].email", is("tomasz@tomaszewski.pl")))
                .andExpect(jsonPath("$[0].department", is("DEVELOPER")))
                .andExpect(jsonPath("$[0].dateOfStartingWork", is("2022-01-01")))
                .andExpect(jsonPath("$[0].dateOfEndingWork").value(nullValue()))
                .andExpect(jsonPath("$[0].isEmployed", is(true)));
    }

    @Test
    public void shouldReturnEmployeeById() throws Exception {
        mockMvc.perform(get(URL_PATH + firstEmployee.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Tomasz")))
                .andExpect(jsonPath("$.surname", is("Tomaszewski")))
                .andExpect(jsonPath("$.email", is("tomasz@tomaszewski.pl")))
                .andExpect(jsonPath("$.department", is("DEVELOPER")))
                .andExpect(jsonPath("$.dateOfStartingWork", is("2022-01-01")))
                .andExpect(jsonPath("$.dateOfEndingWork").value(nullValue()))
                .andExpect(jsonPath("$.isEmployed", is(true)));
    }

    @Test
    public void shouldEditEmployeeById() throws Exception {
        String mockDto = objectMapper.writeValueAsString(employeeDto);
//        IF EMPLOYEE EXISTS
        mockMvc.perform(patch(URL_PATH + secondEmployee.getId())
                        .content(mockDto)
                        .contentType("APPLICATION/JSON"))
                .andDo(print())
                .andExpect(status().isOk());
        EmployeeEntity employee = employeeRepository.getById(secondEmployee.getId());
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(employeeDto.getName(), employee.getName()),
                () -> assertEquals(employeeDto.getSurname(), employee.getSurname()),
                () -> assertEquals(employeeDto.getEmail(), employee.getEmail()),
                () -> assertEquals(employeeDto.getDepartment(), employee.getDepartment()),
                () -> assertEquals(employeeDto.getDateOfStartingWork(), employee.getDateOfStartingWork().toString())
        );

//        IF EMPLOYEE NOT EXISTS
        mockMvc.perform(patch(URL_PATH + 1200)
                        .contentType("APPLICATION/JSON")
                        .content(mockDto))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldMarkEmployeeEntityAsUnemployed() throws Exception {
        String mockDto = objectMapper.writeValueAsString(employeeEndOfWorkDto);
//        IF EMPLOYEE EXISTS
        mockMvc.perform(patch(URL_PATH + secondEmployee.getId() + "/exemptions")
                        .contentType("APPLICATION/JSON")
                        .content(mockDto))
                .andDo(print())
                .andExpect(status().isOk());
        EmployeeEntity employee = employeeRepository.getById(secondEmployee.getId());
        System.out.println(employee);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(employeeEndOfWorkDto.getEndDateOfWork(), employee.getDateOfEndingWork().toString())
        );

//        IF EMPLOYEE NOT EXISTS
        mockMvc.perform(patch(URL_PATH + 1200 + "/exemptions")
                        .contentType("APPLICATION/JSON")
                        .content(mockDto))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRemoveEmployeeEntityFromTheDatabase() throws Exception {
//        IF EMPLOYEE EXISTS
        mockMvc.perform(delete(URL_PATH + firstEmployee.getId()))
                .andDo(print())
                .andExpect(status().isOk());

//    IF EMPLOYEE NOT EXISTS
        mockMvc.perform(delete(URL_PATH + 1200))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}