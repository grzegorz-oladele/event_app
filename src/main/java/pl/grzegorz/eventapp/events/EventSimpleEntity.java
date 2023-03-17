package pl.grzegorz.eventapp.events;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventSimpleEntity that = (EventSimpleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}