package pl.grzegorz.eventapp.participants.dto.output;

import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;

public interface ParticipantOutputDto {

    Long getId();

    EmployeeInEventOutputDto getEmployee();
}