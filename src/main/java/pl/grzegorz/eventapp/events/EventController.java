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

    @PostMapping
    void createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
    }

    @PatchMapping("/{eventId}")
    void editEventById(@PathVariable long eventId, @RequestBody EventDto eventDto) {
        eventService.editEventById(eventId, eventDto);
    }

    @PutMapping("/{eventId}/employees/{employeeId}")
    void addEmployeeToEvent(@PathVariable long eventId, @PathVariable long employeeId) {
        eventService.addEmployeeToEvent(eventId, employeeId);
    }
}