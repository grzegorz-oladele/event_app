package pl.grzegorz.eventapp.organizers.dto;

import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;
import pl.grzegorz.eventapp.organizers.EventRole;

public interface OrganizerOutputDto {

    Long getId();

    EmployeeInEventOutputDto getEmployee();

    EventRole getRole();
}
