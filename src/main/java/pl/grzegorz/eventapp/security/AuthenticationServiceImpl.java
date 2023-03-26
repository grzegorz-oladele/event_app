package pl.grzegorz.eventapp.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.employees.EmployeeDetailsService;

@Service
@RequiredArgsConstructor
@Slf4j
class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final EmployeeDetailsService employeeDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    public String authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails user = employeeDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtils.generateToken(user);
    }
}