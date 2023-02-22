package pl.grzegorz.eventapp.events;

import lombok.*;
import pl.grzegorz.eventapp.events.dto.input.EventDto;
import pl.grzegorz.eventapp.participants.ParticipantSimpleEntity;
import pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
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
@Builder(access = PRIVATE, setterPrefix = "with")
class EventEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String eventName;
    private LocalDateTime startEventTime;
    private LocalDateTime endEventTime;
    private Integer limitOfParticipants;
    private Integer currentParticipantsNumber;
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
                .build();
    }
}