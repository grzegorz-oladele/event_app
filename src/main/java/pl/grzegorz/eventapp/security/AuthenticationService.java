package pl.grzegorz.eventapp.security;

interface AuthenticationService {

    String authenticate(LoginRequest loginRequest);
}