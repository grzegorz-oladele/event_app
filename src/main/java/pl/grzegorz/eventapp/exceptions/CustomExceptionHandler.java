package pl.grzegorz.eventapp.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
class CustomExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(EntityException.class)
    ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityException e) {
        return ResponseEntity.status(NOT_FOUND.value()).body(getErrorResponse(e.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(ParticipantException.class)
    ResponseEntity<ErrorResponse> handleParticipantsLimitException(ParticipantException e) {
        return ResponseEntity.status(NOT_ACCEPTABLE.value()).body(getErrorResponse(e.getMessage(), NOT_ACCEPTABLE));
    }


    private ErrorResponse getErrorResponse(String message, HttpStatus status) {
        return ErrorResponse.builder()
                .withMessage(message)
                .withResponseCode(status.value())
                .withTimestamp(LocalDateTime.now()).withPath(getCurrentUrlPath())
                .build();
    }

    private String getCurrentUrlPath() {
        return request.getServletPath();
    }
}