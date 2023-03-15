package pl.grzegorz.eventapp.exceptions;

import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Builder(setterPrefix = "with")
@AllArgsConstructor(access = PRIVATE)
@Getter(value = PRIVATE)
@Setter(value = PRIVATE)
class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;
    private int responseCode;
    private String path;
}