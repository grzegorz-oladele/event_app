package pl.grzegorz.eventapp.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    String registerEmployee(@RequestBody EmployeeDto employeeDto) {
        return registrationService.register(employeeDto);
    }

    @GetMapping("/confirm")
    String confirm(@RequestParam("registrationToken") String registrationToken) {
        return registrationService.confirmToken(registrationToken);
    }
}