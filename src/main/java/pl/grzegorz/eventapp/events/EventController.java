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

    @PatchMapping("/{eventId}/employees/{mainOrganizerId}")
    void addEmployeeToEventAsOrganizer(@PathVariable long eventId, @PathVariable long mainOrganizerId) {
        eventService.addEmployeeToEventAsOrganizer(eventId, mainOrganizerId);
    }

    @PatchMapping("/{eventId}/employees/{mainOrganizerId}")
    void addEmployeeToEventAsOrganizer(@PathVariable long eventId, @PathVariable long mainOrganizerId) {
        eventService.addEmployeeToEventAsOrganizer(eventId, mainOrganizerId);
    }
}