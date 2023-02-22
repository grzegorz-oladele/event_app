package pl.grzegorz.eventapp.organizer;

import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;
import pl.grzegorz.eventapp.events.EventSimpleEntity;

public interface OrganizerService {

    OrganizerSimpleEntity createOrganizer(EmployeeSimpleEntity employee, EventSimpleEntity event);

}
