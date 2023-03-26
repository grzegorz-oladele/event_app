package pl.grzegorz.eventapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grzegorz.eventapp.employees.EmployeeDetailsService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeDetailsService employeeDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping
    String authenticate(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails user = employeeDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtils.generateToken(user);
    }
}
