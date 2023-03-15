package pl.grzegorz.eventapp.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder(access = PRIVATE, setterPrefix = "with")
public class EventSimpleEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    static EventSimpleEntity toSimpleEntity(EventEntity event) {
        return EventSimpleEntity.builder()
                .withId(event.getId())
                .build();
    }
}