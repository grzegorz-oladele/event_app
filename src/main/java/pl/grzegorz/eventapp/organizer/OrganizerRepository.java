package pl.grzegorz.eventapp.organizer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface OrganizerRepository extends JpaRepository<OrganizerEntity, Long> {

    Optional<OrganizerEntity> findByEmployee_IdAndEvent_Id(long employeeId, long eventId);

}
