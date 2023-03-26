package pl.grzegorz.eventapp.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class LoginRequest {

    private String email;
    private String password;
}
