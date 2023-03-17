package pl.grzegorz.eventapp.organizer;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getSecondEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.getEventSimpleEntity;
import static pl.grzegorz.eventapp.organizer.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizer.EventRole.MAIN_ORGANIZER;

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
}