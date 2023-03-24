package pl.grzegorz.eventapp.organizers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.grzegorz.eventapp.employees.EmployeeTestInitValue;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;
import pl.grzegorz.eventapp.organizers.dto.OrganizerOutputDto;

import java.util.List;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.*;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getSecondEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.getEventSimpleEntity;
import static pl.grzegorz.eventapp.organizers.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizers.EventRole.MAIN_ORGANIZER;

@NoArgsConstructor(access = PRIVATE)
public class OrganizerTestInitValue {

    static OrganizerEntity getOrganizerEntity() {
        return OrganizerEntity.builder()
                .withId(1L)
                .withEmployee(getEmployeeSimpleEntity())
                .withEvent(getEventSimpleEntity())
                .withRole(MAIN_ORGANIZER)
                .build();
    }

    public static List<OrganizerOutputDto> getOrganizers() {
        OrganizerOutputDto mainOrganizer = OrganizerTestOutputDto.builder()
                .withEmployee(EmployeeTestInitValue.getMainOrganizer())
                .withRole(MAIN_ORGANIZER)
                .build();
        OrganizerOutputDto assistant = OrganizerTestOutputDto.builder()
                .withEmployee(getAssistantOutputDto())
                .withRole(ASSISTANT)
                .build();
        return asList(mainOrganizer, assistant);
    }

    public static OrganizerSimpleEntity getOrganizerSimpleEntity() {
        return OrganizerSimpleEntity.builder()
                .withId(1L)
                .withEmployee(getEmployeeSimpleEntity())
                .withRole(MAIN_ORGANIZER)
                .build();
    }

    public static OrganizerSimpleEntity getAssistantOrganizerSimpleEntity() {
        return OrganizerSimpleEntity.builder()
                .withId(2L)
                .withEmployee(getSecondEmployeeSimpleEntity())
                .withRole(ASSISTANT)
                .build();
    }

    @Getter
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class OrganizerTestOutputDto implements OrganizerOutputDto {

        private Long id;
        private EmployeeInEventOutputDto employee;
        private EventRole role;
    }
}