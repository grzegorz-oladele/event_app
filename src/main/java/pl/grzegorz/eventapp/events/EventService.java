package pl.grzegorz.eventapp.events;

import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;

import java.util.List;

public interface EventService {

    List<EventOutputDto> getAllEvents();

    EventOutputDto getEventById(long eventId);

    void createEvent(long employeeId, EventDto eventDto);

    void editEventById(long mainOrganizerId, long eventId, EventDto eventDto);

    void addEmployeeToEventAsOrganizer(long eventId, long mainOrganizerId, long employeeId);
    void addEmployeeToEventAsParticipant(long eventId, long mainOrganizerId, long employeeId);
}