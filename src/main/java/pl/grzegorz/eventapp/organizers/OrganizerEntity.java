package pl.grzegorz.eventapp.organizers;

import lombok.*;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "organizers")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PROTECTED, setterPrefix = "with")
class OrganizerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;
    @OneToOne
    private EventSimpleEntity event;
    @Enumerated(value = EnumType.STRING)
    private EventRole role;

    static OrganizerEntity toEntity(EmployeeSimpleEntity employee, EventSimpleEntity event, EventRole role) {
        return OrganizerEntity.builder()
                .withEmployee(employee)
                .withEvent(event)
                .withRole(role)
                .build();
    }
}