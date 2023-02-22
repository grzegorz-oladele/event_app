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
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE, setterPrefix = "with")
public class ParticipantSimpleEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;
    @OneToOne
    private EventSimpleEntity event;

    public static ParticipantSimpleEntity toSimpleEntity(ParticipantEntity participant) {
        return ParticipantSimpleEntity.builder()
                .withId(participant.getId())
                .withEmployee(participant.getEmployee())
                .withEvent(participant.getEvent())
                .build();
    }
}