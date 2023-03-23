package pl.grzegorz.eventapp.events;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import pl.grzegorz.eventapp.AbstractIntegrationTest;
import pl.grzegorz.eventapp.employees.EmployeeService;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.organizer.OrganizerService;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantService;
import pl.grzegorz.eventapp.participants.ParticipantSimpleEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.*;
import static pl.grzegorz.eventapp.events.EventSimpleEntity.toSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.*;
import static pl.grzegorz.eventapp.organizer.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizer.EventRole.MAIN_ORGANIZER;

public class EventControllerTest extends AbstractIntegrationTest {

    private static final String URI_PATH = "/events/";

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizerService organizerService;
    @Autowired
    private ParticipantService participantService;
    private EventEntity event;
    private EventDto eventDto;
    private EventDto secondEventDto;
    private EmployeeDto employeeDto;
    private EmployeeDto thirdEmployeeDto;
    private EmployeeDto fourthEmployeeDto;
    private EmployeeSimpleEntity employeeOrganizer;
    private EmployeeSimpleEntity secondEmployeeOrganizer;
    private EmployeeSimpleEntity employeeParticipant;
    private EmployeeSimpleEntity secondEmployeeParticipant;
    private OrganizerSimpleEntity organizerSimpleEntity;
    private OrganizerSimpleEntity secondOrganizerSimple;
    private ParticipantSimpleEntity participantSimpleEntity;

    @Before
    public void setup() {
        event = eventRepository.save(getEventEntityForIntegrationTest());
        eventDto = getEventDto();
        secondEventDto = getSecondEventDto();
        employeeDto = getEmployeeDto();
        thirdEmployeeDto = getThirdEmployeeDto();
        fourthEmployeeDto = getFourthEmployeeDto();
        employeeOrganizer = employeeService.createEmployee(employeeDto);
        secondEmployeeOrganizer = employeeService.createEmployee(thirdEmployeeDto);
        employeeParticipant = employeeService.createEmployee(getSecondEmployeeDto());
        secondEmployeeParticipant = employeeService.createEmployee(fourthEmployeeDto);
        organizerSimpleEntity = 
                organizerService.createOrganizer(employeeOrganizer, toSimpleEntity(event), MAIN_ORGANIZER);
        secondOrganizerSimple = organizerService.createOrganizer(secondEmployeeOrganizer, toSimpleEntity(event), ASSISTANT);
        participantSimpleEntity = participantService.createParticipant(employeeParticipant, toSimpleEntity(event));
        event.getOrganizers().add(organizerSimpleEntity);
//        event.getOrganizers().add(secondOrganizerSimple);
        event.getParticipants().add(participantSimpleEntity);
        event = eventRepository.save(event);
    }

    @AfterEach
    void teardown() {
        eventRepository.deleteAll();
        organizerService.removeByEventAndEmployee(event.getId(), employeeOrganizer.getId());
        organizerService.removeByEventAndEmployee(event.getId(), secondEmployeeOrganizer.getId());
        participantService.removeByEventAndEmployee(event.getId(), employeeParticipant.getId());
        participantService.removeByEventAndEmployee(event.getId(), secondEmployeeParticipant.getId());
    }

    @Test
    public void shouldReturnListOfAllEvents() throws Exception {
        mockMvc.perform(get(URI_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].limitOfParticipants", is(2)))
                .andExpect(jsonPath("$[0].eventName", is("Gildia backend")))
                .andExpect(jsonPath("$[0].endEventTime", is("2023-03-17T13:00:00")))
                .andExpect(jsonPath("$[0].startEventTime", is("2023-03-17T12:00:00")))
                .andExpect(jsonPath("$[0].organizers", hasSize(1)))
                .andExpect(jsonPath("$[0].organizers[0].role", is("MAIN_ORGANIZER")))
                .andExpect(jsonPath("$[0].organizers[0].employee.name", is("Paweł")))
                .andExpect(jsonPath("$[0].organizers[0].employee.surname", is("Pawłowski")))
                .andExpect(jsonPath("$[0].organizers[0].employee.department", is("DEVELOPER")))
                .andExpect(jsonPath("$[0].organizers[0].employee.email", is("paweł@pawłowski.pl")))
                .andExpect(jsonPath("$[0].participants", hasSize(1)))
                .andExpect(jsonPath("$[0].participants[0].employee.name", is("Mateusz")))
                .andExpect(jsonPath("$[0].participants[0].employee.surname", is("Matuszczyk")))
                .andExpect(jsonPath("$[0].participants[0].employee.department", is("DEVELOPER")))
                .andExpect(jsonPath("$[0].participants[0].employee.email", is("mateusz@matuszczyk.pl")));
    }

    @Test
    public void shouldReturnEventById() throws Exception {
//        WHEN EVENT EXISTS
        System.out.println(event);
        mockMvc.perform(get(URI_PATH + event.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limitOfParticipants", is(2)))
                .andExpect(jsonPath("$.eventName", is("Gildia backend")))
                .andExpect(jsonPath("$.endEventTime", is("2023-03-17T13:00:00")))
                .andExpect(jsonPath("$.startEventTime", is("2023-03-17T12:00:00")))
                .andExpect(jsonPath("$.organizers", hasSize(1)))
                .andExpect(jsonPath("$.organizers[0].role", is("MAIN_ORGANIZER")))
                .andExpect(jsonPath("$.organizers[0].employee.name", is("Paweł")))
                .andExpect(jsonPath("$.organizers[0].employee.surname", is("Pawłowski")))
                .andExpect(jsonPath("$.organizers[0].employee.department", is("DEVELOPER")))
                .andExpect(jsonPath("$.organizers[0].employee.email", is("paweł@pawłowski.pl")))
                .andExpect(jsonPath("$.participants", hasSize(1)))
                .andExpect(jsonPath("$.participants[0].employee.name", is("Mateusz")))
                .andExpect(jsonPath("$.participants[0].employee.surname", is("Matuszczyk")))
                .andExpect(jsonPath("$.participants[0].employee.department", is("DEVELOPER")))
                .andExpect(jsonPath("$.participants[0].employee.email", is("mateusz@matuszczyk.pl")));

//        WHEN EVENT NOT EXISTS
        mockMvc.perform(get(URI_PATH + 1200))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void shouldCreateNewEvent() throws Exception {
        long mainOrganizerId = employeeOrganizer.getId();
        String mockEventDto = objectMapper.writeValueAsString(eventDto);
//        WHEN MAIN ORGANIZER EXISTS
        mockMvc.perform(post(URI_PATH + mainOrganizerId + "/employees")
                        .content(mockEventDto)
                        .contentType("APPLICATION/JSON"))
                .andDo(print())
                .andExpect(status().isOk());
        List<EventOutputDto> events = eventRepository.findAllBy();
        System.out.println(events);
        assertAll(
                () -> assertNotNull(events),
                () -> assertEquals(2, events.size()),
                () -> assertEquals("Gildia Devops", events.get(1).getEventName()),
                () -> assertEquals(LocalDateTime.parse("2023-03-10T10:00"), events.get(1).getStartEventTime()),
                () -> assertEquals(LocalDateTime.parse("2023-04-10T13:00"), events.get(1).getEndEventTime()),
                () -> assertEquals(3, events.get(1).getLimitOfParticipants())
        );

//        WHEN MAIN ORGANIZER NOT EXISTS
        mockMvc.perform(post(URI_PATH + 1200 + "/employees")
                        .content(mockEventDto)
                        .contentType("APPLICATION/JSON"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldEditEventById() throws Exception {
        long eventId = event.getId();
        long mainOrganizerId = event.getOrganizers().get(0).getEmployee().getId();
        String mockEventDto = objectMapper.writeValueAsString(secondEventDto);
        mockMvc.perform(patch(URI_PATH + eventId + "/employees/" + mainOrganizerId)
                .contentType("APPLICATION/JSON")
                .content(mockEventDto))
                .andDo(print())
                .andExpect(status().isOk());
        assertAll(
                () -> assertNotNull(event),
                () -> assertEquals("Gildia Frontend", event.getEventName()),
                () -> assertEquals(LocalDateTime.parse("2023-03-15T12:00"), event.getStartEventTime()),
                () -> assertEquals(LocalDateTime.parse("2023-04-21T16:00"), event.getEndEventTime()),
                () -> assertEquals(3, event.getLimitOfParticipants()),
                () -> assertEquals(1, event.getOrganizers().size()),
                () -> assertEquals("Paweł", event.getOrganizers().get(0).getEmployee().getName()),
                () -> assertEquals("Pawłowski", event.getOrganizers().get(0).getEmployee().getSurname()),
                () -> assertEquals("paweł@pawłowski.pl", event.getOrganizers().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", event.getOrganizers().get(0).getEmployee().getDepartment()),
                () -> assertEquals(MAIN_ORGANIZER, event.getOrganizers().get(0).getRole()),
                () -> assertEquals(1, event.getParticipants().size()),
                () -> assertEquals("Mateusz", event.getParticipants().get(0).getEmployee().getName()),
                () -> assertEquals("Matuszczyk", event.getParticipants().get(0).getEmployee().getSurname()),
                () -> assertEquals("mateusz@matuszczyk.pl", event.getParticipants().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", event.getParticipants().get(0).getEmployee().getDepartment())
        );
    }

    @Test
    public void shouldAddNewEmployeeToTheEventAsOrganizer() throws Exception {
        long mainOrganizerId = employeeOrganizer.getId();
        long employeeId = secondEmployeeOrganizer.getId();
        String path = URI_PATH + event.getId() + "/organizers/" + mainOrganizerId + "/employees/" + employeeId + "/add";
        System.out.println(event.getOrganizers());
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isOk());
        EventEntity eventEntity = eventRepository.getById(event.getId());
        assertAll(
                () -> assertNotNull(eventEntity),
                () -> assertEquals("Gildia backend", eventEntity.getEventName()),
                () -> assertEquals(LocalDateTime.parse("2023-03-17T12:00"), eventEntity.getStartEventTime()),
                () -> assertEquals(LocalDateTime.parse("2023-03-17T13:00"), eventEntity.getEndEventTime()),
                () -> assertEquals(2, eventEntity.getLimitOfParticipants()),
                () -> assertEquals(2, eventEntity.getOrganizers().size()),
                () -> assertEquals("Paweł", eventEntity.getOrganizers().get(0).getEmployee().getName()),
                () -> assertEquals("Pawłowski", eventEntity.getOrganizers().get(0).getEmployee().getSurname()),
                () -> assertEquals("paweł@pawłowski.pl", eventEntity.getOrganizers().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", eventEntity.getOrganizers().get(0).getEmployee().getDepartment()),
                () -> assertEquals(MAIN_ORGANIZER, eventEntity.getOrganizers().get(0).getRole()),
                () -> assertEquals("Ireneusz", eventEntity.getOrganizers().get(1).getEmployee().getName()),
                () -> assertEquals("Białkowski", eventEntity.getOrganizers().get(1).getEmployee().getSurname()),
                () -> assertEquals("ireneusz@bialkowski.pl", eventEntity.getOrganizers().get(1).getEmployee().getEmail()),
                () -> assertEquals("QA", eventEntity.getOrganizers().get(1).getEmployee().getDepartment()),
                () -> assertEquals(ASSISTANT, eventEntity.getOrganizers().get(1).getRole()),
                () -> assertEquals(1, eventEntity.getParticipants().size()),
                () -> assertEquals("Mateusz", eventEntity.getParticipants().get(0).getEmployee().getName()),
                () -> assertEquals("Matuszczyk", eventEntity.getParticipants().get(0).getEmployee().getSurname()),
                () -> assertEquals("mateusz@matuszczyk.pl", eventEntity.getParticipants().get(0).getEmployee().getEmail()),
                () -> assertEquals("DEVELOPER", eventEntity.getParticipants().get(0).getEmployee().getDepartment())
        );
    }

    @Test
    public void shouldThrowExceptionWhenEmployeeAlreadyExistsInEventAsOrganizer() throws Exception {
        long mainOrganizerId = employeeOrganizer.getId();
        long employeeId = mainOrganizerId;
        String path = URI_PATH + event.getId() + "/organizers/" + mainOrganizerId + "/employees/" + employeeId + "/add";
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowExceptionWhenEmployeeAlreadyExistsInEventAsParticipant() throws Exception {
        long mainOrganizerId = employeeOrganizer.getId();
        long employeeId = employeeParticipant.getId();
        String path = URI_PATH + event.getId() + "/organizers/" + mainOrganizerId + "/employees/" + employeeId + "/add";
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRemoveOrganizerFromEvent() throws Exception {
        long mainOrganizerId = employeeOrganizer.getId();
        long employeeId = secondOrganizerSimple.getId();
        String path = URI_PATH + event.getId() + "/organizers/" + mainOrganizerId + "/employees/" + employeeId + "/remove";
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isOk());
        System.out.println(event);
        assertAll(
                () -> assertNotNull(event),
                () -> assertEquals(1, event.getOrganizers().size())
        );
    }

    @Test
    public void shouldThrowExceptionWhenMainOrganizerIdWillBeNotCorrect() throws Exception {
        long mainOrganizerId = 1200;
        long employeeId = secondOrganizerSimple.getId();
        String path = URI_PATH + event.getId() + "/organizers/" + mainOrganizerId + "/employees/" + employeeId + "/remove";
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddEmployeeToEventAsParticipant() throws Exception {
        long eventId = event.getId();
        long employeeId = secondEmployeeParticipant.getId();
        String path = URI_PATH + eventId + "/employees/" + employeeId + "/add";
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isOk());
        System.out.println(event);
        assertAll(
                () -> assertEquals(2, event.getParticipants().size()),
                () -> assertEquals("Mieczysław", event.getParticipants().get(1).getEmployee().getName()),
                () -> assertEquals("Zasada", event.getParticipants().get(1).getEmployee().getSurname()),
                () -> assertEquals("mieczyslaw@zasada.pl", event.getParticipants().get(1).getEmployee().getEmail()),
                () -> assertEquals("PM", event.getParticipants().get(1).getEmployee().getDepartment())
        );
    }

    @Test
    public void shouldThrowExceptionWhenEmployeeWillBeNotExistsInTheDatabase() throws Exception {
        long eventId = event.getId();
        long employeeId = 1200;
        String path = URI_PATH + eventId + "/employees/" + employeeId + "/add";
        mockMvc.perform(patch(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}