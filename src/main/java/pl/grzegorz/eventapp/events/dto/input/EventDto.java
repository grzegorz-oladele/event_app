package pl.grzegorz.eventapp.events.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder(setterPrefix = "with")
public class EventDto {

    private String eventName;
    private String startEventTime;
    private String endEventTime;
    private int limitOfParticipant;
}