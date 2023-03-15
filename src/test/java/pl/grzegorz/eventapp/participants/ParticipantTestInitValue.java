package pl.grzegorz.eventapp.participants;

import lombok.NoArgsConstructor;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getEmployeeSimpleEntity;

@NoArgsConstructor(access = PRIVATE)
class ParticipantTestInitValue {

    static ParticipantEntity getParticipantEntity() {
        return ParticipantEntity.builder()
                .withId(1L)
                .withEmployee(getEmployeeSimpleEntity())
                .withEvent(new EventSimpleEntity(1L))
                .build();
    }
}
