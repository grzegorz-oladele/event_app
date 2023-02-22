package pl.grzegorz.eventapp.employees;

import lombok.*;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;

import javax.persistence.*;
import java.time.LocalDate;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "employees")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(setterPrefix = "with", access = PRIVATE)
class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String department;
    private LocalDate dateOfStartingWork;
    private LocalDate dateOfEndingWork;
    private Boolean isEmployed;

    static EmployeeEntity toEntity(EmployeeDto employeeDto) {
        return EmployeeEntity.builder()
                .withName(employeeDto.getName())
                .withSurname(employeeDto.getSurname())
                .withEmail(employeeDto.getEmail())
                .withDepartment(employeeDto.getDepartment())
                .withDateOfStartingWork(LocalDate.parse(employeeDto.getDateOfStartingWork()))
                .withIsEmployed(TRUE)
                .build();
    }
}
