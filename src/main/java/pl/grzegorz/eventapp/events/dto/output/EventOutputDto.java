package pl.grzegorz.eventapp.events.dto.output;

import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventOutputDto {

    Long getId();

    String getEventName();

    LocalDateTime getStartEventTime();

    LocalDateTime getEndEventTime();

    Integer getLimitOfParticipants();

    List<EmployeeInEventOutputDto> getParticipants();
}
