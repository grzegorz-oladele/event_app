package pl.grzegorz.eventapp.organizers;

import lombok.*;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "organizers")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PROTECTED, setterPrefix = "with")
@ToString
public class OrganizerSimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;
    @Enumerated(value = EnumType.STRING)
    private EventRole role;

    static OrganizerSimpleEntity toSimpleEntity(OrganizerEntity organizer) {
        return OrganizerSimpleEntity.builder()
                .withId(organizer.getId())
                .withEmployee(organizer.getEmployee())
                .build();
    }
}