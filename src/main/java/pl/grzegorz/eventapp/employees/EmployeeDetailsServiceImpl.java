package pl.grzegorz.eventapp.employees;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.registration.token.ConfirmationToken;
import pl.grzegorz.eventapp.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

import static pl.grzegorz.eventapp.employees.EmployeeEntity.toEntity;
import static pl.grzegorz.eventapp.employees.EmployeeSimpleEntity.toSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Employee with email {} not found", email);
                    throw new UsernameNotFoundException("Employee not found");
                });
    }

    @Transactional
    @Override
    public String signUpEmployee(EmployeeDto employeeDto) {
        boolean employeeExists = employeeRepository.findByEmail(employeeDto.getEmail())
                .isPresent();
        if (employeeExists) {
            throw new IllegalArgumentException("Email already taken");
        }
        EmployeeEntity employee = toEntity(employeeDto);
        String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employeeRepository.save(employee);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .withToken(token)
                .withCreatedAt(LocalDateTime.now())
                .withExpiresAt(LocalDateTime.now().plusMinutes(15))
                .withEmployee(toSimpleEntity(employee))
                .build();
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    @Override
    public int enableEmployee(String email) {
        return employeeRepository.enableEmployeeEntity(email);
    }
}