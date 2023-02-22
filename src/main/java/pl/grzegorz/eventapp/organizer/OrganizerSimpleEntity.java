package pl.grzegorz.eventapp.organizer;

import lombok.*;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;
import pl.grzegorz.eventapp.organizer.EventRole;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static pl.grzegorz.eventapp.organizer.EventRole.ASSISTANT;

@Entity
@Table(name = "organizers")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE, setterPrefix = "with")
public class OrganizerSimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private EmployeeSimpleEntity employee;
    @OneToOne
    private EventSimpleEntity event;
    @Enumerated(value = EnumType.STRING)
    private EventRole role;

    public void setAssistantRole() {
        this.role = ASSISTANT;
    }

    public static OrganizerSimpleEntity toSimpleEntity(OrganizerEntity organizer) {
        return OrganizerSimpleEntity.builder()
                .withId(organizer.getId())
                .withEmployee(organizer.getEmployee())
                .withEvent(organizer.getEvent())
                .withRole(organizer.getRole())
                .build();
    }
}