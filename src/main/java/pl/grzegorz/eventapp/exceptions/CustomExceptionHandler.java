package pl.grzegorz.eventapp.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
class CustomExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return getErrorResponse(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(OrganizerException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handleOrganizerException(OrganizerException e) {
        return getErrorResponse(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ParticipantException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse handleParticipantException(ParticipantException e) {
        return getErrorResponse(e.getMessage(), BAD_REQUEST);
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