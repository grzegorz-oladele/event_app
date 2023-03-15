package pl.grzegorz.eventapp.events.dto.output;

import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;
import pl.grzegorz.eventapp.organizer.dto.OrganizerOutputDto;
import pl.grzegorz.eventapp.participants.dto.output.ParticipantOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventOutputDto {

    Long getId();

    String getEventName();

    LocalDateTime getStartEventTime();

    LocalDateTime getEndEventTime();

    Integer getLimitOfParticipants();

    List<ParticipantOutputDto> getParticipants();

    List<OrganizerOutputDto> getOrganizers();
}
