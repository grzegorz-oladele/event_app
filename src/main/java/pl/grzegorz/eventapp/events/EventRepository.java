package pl.grzegorz.eventapp.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grzegorz.eventapp.events.dto.output.EventOutputDto;
import pl.grzegorz.eventapp.employees.dto.simple_entity.EmployeeSimpleEntity;

import java.util.List;
import java.util.Optional;

@Repository
interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventOutputDto> findAllBy();

    Optional<EventOutputDto> findAllById(long eventId);

    @Query("SELECT e.participants FROM EventEntity e WHERE e.id = ?1")
    Optional<List<EmployeeSimpleEntity>> findAllEmployeesByEventId(long eventId);
}
