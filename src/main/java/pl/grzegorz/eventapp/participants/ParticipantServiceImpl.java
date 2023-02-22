package pl.grzegorz.eventapp.participants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

import static pl.grzegorz.eventapp.participants.ParticipantSimpleEntity.toSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public ParticipantSimpleEntity createParticipant(EmployeeSimpleEntity employee, EventSimpleEntity event) {
        ParticipantEntity participantEntity = ParticipantEntity.toEntity(employee, event);
        participantRepository.save(participantEntity);
        return toSimpleEntity(participantEntity);
    }
}