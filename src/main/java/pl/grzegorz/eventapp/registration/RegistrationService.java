package pl.grzegorz.eventapp.registration;

import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;

public interface RegistrationService {

    String register(EmployeeDto employeeDto);

    String confirmToken(String registrationToken);
}