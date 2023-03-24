package pl.grzegorz.eventapp.organizers;

import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

public interface OrganizerService {

    OrganizerSimpleEntity createOrganizer(EmployeeSimpleEntity employeeSimple, EventSimpleEntity eventSimple, EventRole role);

    void removeByEventAndEmployee(long eventId, long employeeId);
}