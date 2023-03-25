package pl.grzegorz.eventapp.registration.token;

import lombok.*;
import pl.grzegorz.eventapp.employees.EmployeeSimpleEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "confirmation_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public
class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy =
            GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence")
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "employee_id"
    )
    private EmployeeSimpleEntity employee;
}