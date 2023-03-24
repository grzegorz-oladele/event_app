package pl.grzegorz.eventapp.events;

import lombok.*;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.organizers.OrganizerSimpleEntity;
import pl.grzegorz.eventapp.participants.ParticipantSimpleEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.parse;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "events")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PROTECTED, setterPrefix = "with")
@ToString
class EventEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String eventName;
    private LocalDateTime startEventTime;
    private LocalDateTime endEventTime;
    private int limitOfParticipants;
    private int currentParticipantsNumber;
    @ManyToMany
    private List<OrganizerSimpleEntity> organizers;
    @ManyToMany
    private List<ParticipantSimpleEntity> participants;

    static EventEntity toEntity(EventDto eventDto) {
        return EventEntity.builder()
                .withEventName(eventDto.getEventName())
                .withStartEventTime(parse(eventDto.getStartEventTime()))
                .withEndEventTime(parse(eventDto.getEndEventTime()))
                .withLimitOfParticipants(eventDto.getLimitOfParticipant())
                .withOrganizers(new ArrayList<>())
                .withParticipants(new ArrayList<>())
                .build();
    }
}