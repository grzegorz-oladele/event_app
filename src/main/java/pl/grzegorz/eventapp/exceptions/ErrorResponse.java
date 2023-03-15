package pl.grzegorz.eventapp.exceptions;

import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;
    private int responseCode;
    private String path;
}