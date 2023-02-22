package pl.grzegorz.eventapp.events.dto.simple_entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "events")
@Getter
public class EventSimpleEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String eventName;
    private LocalDateTime startEventTime;
    private LocalDateTime endEventTime;
}
