package pl.grzegorz.eventapp.participants;

import lombok.*;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "participants")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(setterPrefix = "with")
@ToString
public class ParticipantSimpleEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;

    static ParticipantSimpleEntity toSimpleEntity(ParticipantEntity participant) {
        return ParticipantSimpleEntity.builder()
                .withId(participant.getId())
                .withEmployee(participant.getEmployee())
                .build();
    }
}