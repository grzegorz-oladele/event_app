package pl.grzegorz.eventapp.employees;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "employees")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PUBLIC)
@AllArgsConstructor(access = PUBLIC)
@Builder(setterPrefix = "with", access = PROTECTED)
@ToString
public class EmployeeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String department;
    private LocalDate dateOfStartingWork;
    private LocalDate dateOfEndingWork;
    private Boolean isEmployed;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<AppUserRole> roles = new ArrayList<>();
    private Boolean locked = FALSE;
    private Boolean enabled = FALSE;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}