package pl.grzegorz.eventapp.participants;

import lombok.*;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "members")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE, setterPrefix = "with")
class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;
    @OneToOne
    private EventSimpleEntity event;

    static ParticipantEntity toEntity(EmployeeSimpleEntity employee, EventSimpleEntity event) {
        return ParticipantEntity.builder()
                .withEmployee(employee)
                .withEvent(event)
                .build();
    }
}