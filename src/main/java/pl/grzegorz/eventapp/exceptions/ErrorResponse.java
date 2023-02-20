package pl.grzegorz.eventapp.exceptions;

import lombok.*;

import java.time.LocalDateTime;
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;
    private int responseCode;
    private String path;
}