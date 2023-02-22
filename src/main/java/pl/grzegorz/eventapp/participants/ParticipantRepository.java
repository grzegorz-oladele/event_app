package pl.grzegorz.eventapp.participants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {

}