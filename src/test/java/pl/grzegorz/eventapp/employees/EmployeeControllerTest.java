package pl.grzegorz.eventapp.employees;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import pl.grzegorz.eventapp.AbstractIntegrationTest;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getFirstEmployeeEntity;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getSecondEmployeeEntity;

public class EmployeeControllerTest extends AbstractIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        employeeRepository.save(getFirstEmployeeEntity());
        employeeRepository.save(getSecondEmployeeEntity());
    }

    @Test
    public void shouldReturnListOfEmployees() throws Exception {
        String result = mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<EmployeeOutputDto> employees = objectMapper.readValue(result, new TypeReference<>(){});
        System.out.println(employees);
    }
}