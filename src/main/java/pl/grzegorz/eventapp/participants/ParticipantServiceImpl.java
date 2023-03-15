package pl.grzegorz.eventapp.participants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import javax.persistence.EntityNotFoundException;

import static pl.grzegorz.eventapp.participants.ParticipantEntity.toEntity;
import static pl.grzegorz.eventapp.participants.ParticipantSimpleEntity.toSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public ParticipantSimpleEntity createParticipant(EmployeeSimpleEntity employeeSimple, EventSimpleEntity eventSimple) {
        ParticipantEntity participant = toEntity(employeeSimple, eventSimple);
        ParticipantEntity savedParticipant = participantRepository.save(participant);
        log.info("Create new participant with id -> {}", savedParticipant.getId());
        return toSimpleEntity(participant);
    }

    @Override
    public void removeByEventAndEmployee(long eventId, long employeeId) {
        ParticipantEntity participant = getParticipantEntity(eventId, employeeId);
        participantRepository.delete(participant);
        log.info("Remove participant with id -> {} using employee id -> {}", participant.getId(), employeeId);
    }

    private ParticipantEntity getParticipantEntity(long eventId, long employeeId) {
        return participantRepository.findByEmployee_IdAndEvent_Id(employeeId, eventId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Participant not found");
                });
    }
}