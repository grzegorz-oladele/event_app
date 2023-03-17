package pl.grzegorz.eventapp.employees;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "employees")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder(access = PROTECTED, setterPrefix = "with")
public class EmployeeSimpleEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String department;

    public static EmployeeSimpleEntity toSimpleEntity(EmployeeEntity employee) {
        return EmployeeSimpleEntity.builder()
                .withId(employee.getId())
                .withName(employee.getName())
                .withSurname(employee.getSurname())
                .withEmail(employee.getEmail())
                .build();
    }
}