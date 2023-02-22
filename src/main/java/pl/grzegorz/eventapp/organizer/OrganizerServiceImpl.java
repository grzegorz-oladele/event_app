package pl.grzegorz.eventapp.organizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import static pl.grzegorz.eventapp.organizer.EventRole.ASSISTANT;
import static pl.grzegorz.eventapp.organizer.OrganizerEntity.toEntity;
import static pl.grzegorz.eventapp.organizer.OrganizerSimpleEntity.toSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;

    @Override
    public OrganizerSimpleEntity createOrganizer(EmployeeSimpleEntity employee, EventSimpleEntity event) {
        OrganizerEntity organizerEntity = toEntity(employee, event, ASSISTANT);
        organizerRepository.save(organizerEntity);
        return toSimpleEntity(organizerEntity);
    }
}