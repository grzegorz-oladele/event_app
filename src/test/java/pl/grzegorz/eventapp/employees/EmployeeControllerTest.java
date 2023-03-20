package pl.grzegorz.eventapp.employees;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.grzegorz.eventapp.AbstractIntegrationTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getFirstEmployeeEntity;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getSecondEmployeeEntity;

public class EmployeeControllerTest extends AbstractIntegrationTest {

    private static final String URL_PATH = "/employees";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        employeeRepository.save(getFirstEmployeeEntity());
        employeeRepository.save(getSecondEmployeeEntity());
    }

    @Test
    public void shouldReturnListOfEmployees() throws Exception {
        mockMvc.perform(get(URL_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Tomasz")))
                .andExpect(jsonPath("$[0].surname", is("Tomaszewski")))
                .andExpect(jsonPath("$[0].email", is("tomasz@tomaszewski.pl")))
                .andExpect(jsonPath("$[0].department", is("DEVELOPER")))
                .andExpect(jsonPath("$[0].dateOfStartingWork", is("2022-01-01")))
                .andExpect(jsonPath("$[0].dateOfEndingWork").value(nullValue()))
                .andExpect(jsonPath("$[0].isEmployed", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Michał")))
                .andExpect(jsonPath("$[1].surname", is("Michałowski")))
                .andExpect(jsonPath("$[1].email", is("michal@michalowski.pl")))
                .andExpect(jsonPath("$[1].dateOfStartingWork", is("2022-05-01")))
                .andExpect(jsonPath("$[1].dateOfEndingWork").value(nullValue()))
                .andExpect(jsonPath("$[1].isEmployed", is(false)));
    }
}