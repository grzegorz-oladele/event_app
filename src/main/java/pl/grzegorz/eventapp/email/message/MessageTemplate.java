package pl.grzegorz.eventapp.email.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum MessageTemplate {

    CREATE_EMPLOYEE_MESSAGE_TEMPLATE("Zarejestrowano w aplikacji z użyciem maila %-1s");

    private final String message;
}
