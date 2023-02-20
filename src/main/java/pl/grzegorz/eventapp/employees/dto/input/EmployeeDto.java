package pl.grzegorz.eventapp.employees.dto.input;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class EmployeeDto {

    private String name;
    private String surname;
    private String email;
    private String department;
    private String dateOfStartingWork;
}
