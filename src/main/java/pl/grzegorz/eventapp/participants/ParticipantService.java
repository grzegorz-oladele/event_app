package pl.grzegorz.eventapp.participants;

import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

public interface ParticipantService {

    ParticipantSimpleEntity createParticipant(EmployeeSimpleEntity employeeSimple, EventSimpleEntity eventSimple);

    void removeByEventAndEmployee(long eventId, long employeeId);
}
