package pl.grzegorz.eventapp.exceptions;

import lombok.*;

import java.time.LocalDateTime;

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