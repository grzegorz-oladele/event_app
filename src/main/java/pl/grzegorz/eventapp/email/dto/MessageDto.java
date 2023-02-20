package pl.grzegorz.eventapp.email.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder(setterPrefix = "with")
public class MessageDto {

    private String to;
    private String subject;
    private String content;
}