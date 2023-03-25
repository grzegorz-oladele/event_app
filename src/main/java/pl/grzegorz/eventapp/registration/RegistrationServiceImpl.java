package pl.grzegorz.eventapp.registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grzegorz.eventapp.employees.EmployeeDetailsService;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.registration.token.ConfirmationToken;
import pl.grzegorz.eventapp.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
class RegistrationServiceImpl implements RegistrationService {

    private static final String ACTIVATION_LINK = "http://localhost:8080/api/registration/confirm?token=";

    private final EmailValidator emailValidator;
    private final EmployeeDetailsService employeeDetailsService;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public String register(EmployeeDto employeeDto) {
        boolean isEmailValid = emailValidator.test(employeeDto.getEmail());
        if (!isEmailValid) {
            throw new IllegalArgumentException("Email not valid");
        }
        String registrationToken = employeeDetailsService.signUpEmployee(employeeDto);
        String link = ACTIVATION_LINK + registrationToken;
        return registrationToken;
    }

    @Transactional
    @Override
    public String confirmToken(String registrationToken) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(registrationToken);
        if (Objects.nonNull(confirmationToken.getConfirmedAt())) {
            throw new IllegalArgumentException("Email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }
        confirmationTokenService.setConfirmedAt(registrationToken);
        employeeDetailsService.enableEmployee(confirmationToken.getEmployee().getEmail());
        return "Confirmed!";
    }
}