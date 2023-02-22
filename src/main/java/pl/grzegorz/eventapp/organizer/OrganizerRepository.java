package pl.grzegorz.eventapp.organizer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrganizerRepository extends JpaRepository<OrganizerEntity, Long> {
}
