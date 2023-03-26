package pl.grzegorz.eventapp.exceptions;

import  lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return getErrorResponse(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return getErrorResponse(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(OrganizerException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handleOrganizerException(OrganizerException e) {
        return getErrorResponse(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        return getErrorResponse(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(ParticipantException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handleParticipantException(ParticipantException e) {
        return getErrorResponse(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationHeaderException.class)
    @ResponseStatus(FORBIDDEN)
    ErrorResponse handleCredentialNotFoundException(AuthorizationHeaderException e) {
        return getErrorResponse(e.getMessage(), FORBIDDEN);
    }

    private ErrorResponse getErrorResponse(String message, HttpStatus status) {
        return ErrorResponse.builder()
                .withMessage(message)
                .withResponseCode(status.value())
                .withTimestamp(LocalDateTime.now())
                .withPath(getCurrentUrlPath())
                .build();
    }

    private String getCurrentUrlPath() {
        return request.getServletPath();
    }
}