package pl.grzegorz.eventapp.employees;

import pl.grzegorz.eventapp.employees.dto.input.EmployeeEndOfWorkDto;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeOutputDto> getAllEmployees();

    List<EmployeeOutputDto> getAllHiredEmployees();

    EmployeeOutputDto getEmployeeById(long participantId);

    EmployeeSimpleEntity getEmployeeSimpleEntityById(long employeeId);

    void createEmployee(EmployeeDto employeeDto);

    void editEmployee(long participantId, EmployeeDto employeeDto);

    void setEmployeeAsUnemployed(EmployeeEndOfWorkDto employeeEndOfWorkDto);

    void removeEmployee(long participantId);
}