package pl.grzegorz.eventapp.employees.dto.input;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class EmployeeEndOfWorkDto {

    private long employeeId;
    private String endDateOfWork;
}