package pl.grzegorz.eventapp.participants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {

    Optional<ParticipantEntity> findByEmployee_IdAndEvent_Id(long employeeId, long eventId);
}