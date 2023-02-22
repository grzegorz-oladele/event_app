package pl.grzegorz.eventapp.employees;

import pl.grzegorz.eventapp.employees.dto.input.EmployeeEndOfWorkDto;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeOutputDto> getAllParticipants();

    List<EmployeeOutputDto> getAllEmployedParticipants();

    EmployeeOutputDto getParticipantById(long participantId);

    EmployeeSimpleEntity getWorkingEmployeeSimpleEntityById(long employeeId);

    void createEmployee(EmployeeDto employeeDto);

    void editParticipant(long participantId, EmployeeDto employeeDto);

    void setParticipantAsUnemployed(EmployeeEndOfWorkDto employeeEndOfWorkDto);

    void removeParticipant(long participantId);
}