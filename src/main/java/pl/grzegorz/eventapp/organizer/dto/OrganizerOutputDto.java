package pl.grzegorz.eventapp.organizer.dto;

import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;
import pl.grzegorz.eventapp.organizer.EventRole;

public interface OrganizerOutputDto {

    Long getId();

    EmployeeInEventOutputDto getEmployee();

    EventRole getRole();
}
