package pl.grzegorz.eventapp.events;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE, setterPrefix = "with")
public class EventSimpleEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String eventName;
    private LocalDateTime startEventTime;
    private LocalDateTime endEventTime;

    public static EventSimpleEntity toSimpleEntity(EventEntity event) {
        return EventSimpleEntity.builder()
                .withId(event.getId())
                .withEventName(event.getEventName())
                .withStartEventTime(event.getStartEventTime())
                .withEndEventTime(event.getEndEventTime())
                .build();
    }
}