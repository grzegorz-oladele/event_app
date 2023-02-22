package pl.grzegorz.eventapp.organizer;

import lombok.*;
import pl.grzegorz.eventapp.employees.dto.simple_entity.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.dto.simple_entity.EventSimpleEntity;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "organizers")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE, setterPrefix = "with")
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