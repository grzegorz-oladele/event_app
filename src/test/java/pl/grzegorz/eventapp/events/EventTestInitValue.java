package pl.grzegorz.eventapp.events;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EventTestInitValue {

    public static EventSimpleEntity getEventSimpleEntity() {
        return new EventSimpleEntity(1L);
    }
}
