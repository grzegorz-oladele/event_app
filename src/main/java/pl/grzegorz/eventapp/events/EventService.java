package pl.grzegorz.eventapp.events;

import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;

import java.util.List;

public interface EventService {

    List<EventOutputDto> getAllEvents();

    EventOutputDto getEventById(long eventId);

    void createEvent(long employeeId, EventDto eventDto);

    void editEventById(long mainOrganizerId, long eventId, EventDto eventDto);

    void addEmployeeAsOrganizer(long mainOrganizerId, long employeeId, long eventId);

    void removeOrganizerFromEvent(long mainOrganizerId, long employeeId, long eventId);

    void addEmployeeAsParticipant(long eventId, long employeeId);

    void removeParticipantFromEvent(long eventId, long employeeId);
}