package pl.grzegorz.eventapp.organizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import javax.persistence.EntityNotFoundException;

import static pl.grzegorz.eventapp.organizer.OrganizerEntity.toEntity;
import static pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity.toSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;

    @Override
    public OrganizerSimpleEntity createOrganizer(EmployeeSimpleEntity employeeSimple, EventSimpleEntity eventSimple, EventRole role) {
        OrganizerEntity organizer = toEntity(employeeSimple, eventSimple, role);
        OrganizerEntity savedOrganizer = organizerRepository.save(organizer);
        log.info("Create new organizer");
        return toSimpleEntity(savedOrganizer);
    }

    @Override
    public void removeByEventAndEmployee(long eventId, long employeeId) {
        OrganizerEntity organizer = getOrganizerEntity(eventId, employeeId);
        organizerRepository.delete(organizer);
    }

    private OrganizerEntity getOrganizerEntity(long eventId, long employeeId) {
        return organizerRepository.findByEmployee_IdAndEvent_Id(employeeId, eventId)
                .orElseThrow(() -> {
                    log.error("Organizer using id of employee -> {} not found", employeeId);
                    throw new EntityNotFoundException("Organizer not found");
                });
    }
}