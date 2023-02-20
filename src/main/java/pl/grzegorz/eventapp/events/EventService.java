package pl.grzegorz.eventapp.events;

import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;

import java.util.List;

public interface EventService {

    List<EventOutputDto> getAllEvents();

    EventOutputDto getEventById(long eventId);

    void createEvent(EventDto eventDto);

    void editEventById(long eventId, EventDto eventDto);

    void addEmployeeToEvent(long eventId, long employeeId);
}