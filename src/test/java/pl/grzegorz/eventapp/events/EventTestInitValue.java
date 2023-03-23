package pl.grzegorz.eventapp.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.organizer.OrganizerTestInitValue;
import pl.grzegorz.eventapp.organizer.dto.OrganizerOutputDto;
import pl.grzegorz.eventapp.participants.ParticipantSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantTestInitValue;
import pl.grzegorz.eventapp.participants.dto.output.ParticipantOutputDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.parse;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getAssistantOrganizerSimpleEntity;
import static pl.grzegorz.eventapp.organizer.OrganizerTestInitValue.getOrganizerSimpleEntity;
import static pl.grzegorz.eventapp.participants.ParticipantTestInitValue.getParticipantSimpleEntity;

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
                .withOrganizers(OrganizerTestInitValue.getOrganizers())
                .withParticipants(ParticipantTestInitValue.getParticipants())
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

    static EventDto getSecondEventDto() {
        return EventDto.builder()
                .withEventName("Gildia Frontend")
                .withStartEventTime("2023-03-15T12:00")
                .withEndEventTime("2023-04-21T16:00")
                .withLimitOfParticipant(3)
                .build();
    }

    static EventEntity getEventEntity() {
        return EventEntity.builder()
                .withId(1L)
                .withEventName("Gildia backend")
                .withCurrentParticipantsNumber(0)
                .withStartEventTime(parse("2023-03-17T12:00"))
                .withEndEventTime(parse("2023-03-17T13:00"))
                .withOrganizers(getOrganizers())
                .withParticipants(getParticipants())
                .withLimitOfParticipants(2)
                .build();
    }

    static EventEntity getEventEntityForIntegrationTest() {
        return EventEntity.builder()
                .withEventName("Gildia backend")
                .withStartEventTime(parse("2023-03-17T12:00"))
                .withEndEventTime(parse("2023-03-17T13:00"))
                .withOrganizers(new ArrayList<>())
                .withParticipants(new ArrayList<>())
                .withLimitOfParticipants(2)
                .build();
    }

    static EventEntity getEventEntityWithFullParticipants() {
        return EventEntity.builder()
                .withId(1L)
                .withEventName("Gildia backend")
//                .withCurrentParticipantsNumber(0)
                .withStartEventTime(parse("2023-03-17T12:00"))
                .withEndEventTime(parse("2023-03-17T13:00"))
                .withOrganizers(getOrganizers())
                .withParticipants(getParticipants())
                .withLimitOfParticipants(1)
                .build();
    }

    @NotNull
    private static List<ParticipantSimpleEntity> getParticipants() {
        List<ParticipantSimpleEntity> participants = new ArrayList<>();
        participants.add(getParticipantSimpleEntity());
        return participants;
    }

    @NotNull
    private static List<OrganizerSimpleEntity> getOrganizers() {
        List<OrganizerSimpleEntity> organizers = new ArrayList<>();
        organizers.add(getOrganizerSimpleEntity());
        organizers.add(getAssistantOrganizerSimpleEntity());
        return organizers;
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
}