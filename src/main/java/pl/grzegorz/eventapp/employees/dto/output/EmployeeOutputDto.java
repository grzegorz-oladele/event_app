package pl.grzegorz.eventapp.employees.dto.output;

import java.time.LocalDate;

public interface EmployeeOutputDto {

    Long getId();

    String getName();

    String getSurname();

    String getEmail();

    String getDepartment();

    LocalDate getDateOfStartingWork();

    LocalDate getDateOfEndingWork();

    Boolean getIsEmployed();
}