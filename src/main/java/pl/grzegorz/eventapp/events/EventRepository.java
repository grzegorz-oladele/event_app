package pl.grzegorz.eventapp.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventOutputDto> findAllBy();

    Optional<EventOutputDto> findAllById(long eventId);
}