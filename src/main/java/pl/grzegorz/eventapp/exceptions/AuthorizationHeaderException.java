package pl.grzegorz.eventapp.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorizationHeaderException extends RuntimeException {

    private final String message;
}