package pl.grzegorz.eventapp.employees.dto.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEndOfWorkDto {

    private String endDateOfWork;
}