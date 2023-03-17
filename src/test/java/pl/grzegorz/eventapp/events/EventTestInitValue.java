package pl.grzegorz.eventapp.events;

import lombok.NoArgsConstructor;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.parse;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getOrganizerSimpleEntity;

@NoArgsConstructor(access = PRIVATE)
public class EventTestInitValue {

    public static EventSimpleEntity getEventSimpleEntity() {
        return new EventSimpleEntity(1L);
    }

    static EventEntity getEventEntity() {
        List<OrganizerSimpleEntity> organizers = new ArrayList<>();
        organizers.add(getOrganizerSimpleEntity());
        return EventEntity.builder()
                .withId(1L)
                .withEventName("Gildia backend")
                .withCurrentParticipantsNumber(0)
                .withStartEventTime(parse("2023-03-17T12:00"))
                .withEndEventTime(parse("2023-03-17T13:00"))
                .withOrganizers(organizers)
                .withParticipants(new ArrayList<>())
                .withLimitOfParticipants(2)
                .build();
    }
}