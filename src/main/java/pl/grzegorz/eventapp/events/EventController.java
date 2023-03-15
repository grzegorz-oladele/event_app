package pl.grzegorz.eventapp.events;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
class EventController {

    private final EventService eventService;

    @GetMapping
    List<EventOutputDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    EventOutputDto getEventById(@PathVariable long eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping("/{employeeId}/employees")
    void createEvent(@PathVariable long employeeId ,@RequestBody EventDto eventDto) {
        eventService.createEvent(employeeId, eventDto);
    }

    @PatchMapping("/{eventId}/employees/{mainOrganizerId}")
    void editEventById(@PathVariable long mainOrganizerId, @PathVariable long eventId, @RequestBody EventDto eventDto) {
        eventService.editEventById(mainOrganizerId, eventId, eventDto);
    }

    @PatchMapping("/{eventId}/organizers/{organizerId}/employees/{employeeId}/add")
    void addEmployeeToEventAsOrganizer(@PathVariable long eventId, @PathVariable long organizerId, @PathVariable long employeeId) {
        eventService.addEmployeeAsOrganizer(organizerId, employeeId, eventId);
    }

    @PatchMapping("/{eventId}/organizers/{organizerId}/employees/{employeeId}/remove")
    void removeOrganizerFromAnEvent(@PathVariable long eventId, @PathVariable long organizerId, @PathVariable long employeeId) {
        eventService.removeOrganizerFromEvent(organizerId, employeeId, eventId);
    }

    @PatchMapping("/{eventId}/employees/{employeeId}/add")
    void addEmployeeToEventAsParticipant(@PathVariable long eventId, @PathVariable long employeeId) {
        eventService.addEmployeeAsParticipant(eventId, employeeId);
    }

    @PatchMapping("/{eventId}/employees/{employeeId}/remove")
    void removeOrganizerFromAnEvent(@PathVariable long eventId, @PathVariable long employeeId) {
        eventService.removeParticipantFromEvent(eventId, employeeId);
    }
}