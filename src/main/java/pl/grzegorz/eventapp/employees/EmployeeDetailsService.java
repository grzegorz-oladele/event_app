package pl.grzegorz.eventapp.employees;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;

public interface EmployeeDetailsService extends UserDetailsService {

    String signUpEmployee(EmployeeDto employeeDto);

    int enableEmployee(String email);
}