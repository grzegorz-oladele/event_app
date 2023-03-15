package pl.grzegorz.eventapp.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@RequiredArgsConstructor
class CustomExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return status(NOT_FOUND.value()).body(getErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({OrganizerException.class, ParticipantException.class})
    ResponseEntity<ErrorResponse> handleOrganizerException(OrganizerException e) {
        return status(BAD_REQUEST.value()).body(getErrorResponse(e.getMessage()));
    }

    private ErrorResponse getErrorResponse(String message) {
        return ErrorResponse.builder()
                .withMessage(message)
                .withResponseCode(NOT_FOUND.value())
                .withTimestamp(LocalDateTime.now())
                .withPath(getCurrentUrlPath())
                .build();
    }

    private String getCurrentUrlPath() {
        return request.getServletPath();
    }
}