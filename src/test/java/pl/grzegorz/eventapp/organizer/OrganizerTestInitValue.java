package pl.grzegorz.eventapp.organizer;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getEmployeeSimpleEntity;
import static pl.grzegorz.eventapp.events.EventTestInitValue.getEventSimpleEntity;
import static pl.grzegorz.eventapp.organizer.EventRole.MAIN_ORGANIZER;

@NoArgsConstructor(access = PRIVATE)
class OrganizerTestInitValue {

    static OrganizerEntity getOrganizerEntity() {
        return OrganizerEntity.builder()
                .withId(1L)
                .withEmployee(getEmployeeSimpleEntity())
                .withEvent(getEventSimpleEntity())
                .withRole(MAIN_ORGANIZER)
                .build();
    }
}