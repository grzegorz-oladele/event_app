package pl.grzegorz.eventapp.members;

import lombok.*;
import pl.grzegorz.eventapp.employees.dto.simple_entity.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.dto.simple_entity.EventSimpleEntity;

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
class MemberEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;
    @OneToOne
    private EventSimpleEntity event;

    static MemberEntity toEntity(EmployeeSimpleEntity employee, EventSimpleEntity event) {
        return MemberEntity.builder()
                .withEmployee(employee)
                .withEvent(event)
                .build();
    }
}