package pl.grzegorz.eventapp.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.organizer.dto.OrganizerOutputDto;
import pl.grzegorz.eventapp.participants.dto.output.ParticipantOutputDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.parse;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getOrganizerSimpleEntity;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getOrganizers;
import static pl.grzegorz.eventapp.participants.ParticipantTestInitValue.getParticipants;

@NoArgsConstructor(access = PRIVATE)
public class EventTestInitValue {

    public static EventSimpleEntity getEventSimpleEntity() {
        return new EventSimpleEntity(1L);
    }

    static EventOutputDto getEventOutputDto() {
        return EventTestOutputDto.builder()
                .withId(1L)
                .withEventName("Gildia Java")
                .withStartEventTime(parse("2023-03-20T10:00"))
                .withEndEventTime(parse("2023-03-20T12:00"))
                .withLimitOfParticipants(2)
                .withCurrentParticipantsNumber(2)
                .withOrganizers(getOrganizers())
                .withParticipants(getParticipants())
                .build();
    }

    static EventDto getEventDto() {
        return EventDto.builder()
                .withEventName("Gildia Devops")
                .withStartEventTime("2023-03-10T10:00")
                .withEndEventTime("2023-04-10T13:00")
                .withLimitOfParticipant(3)
                .build();
    }

    @Getter
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    static class EventTestOutputDto implements EventOutputDto {

        private Long id;
        private String eventName;
        private LocalDateTime startEventTime;
        private LocalDateTime endEventTime;
        private Integer limitOfParticipants;
        private Integer currentParticipantsNumber;
        private List<ParticipantOutputDto> participants;
        private List<OrganizerOutputDto> organizers;
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