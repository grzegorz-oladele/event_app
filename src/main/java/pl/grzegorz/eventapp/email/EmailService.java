package pl.grzegorz.eventapp.email;

import pl.grzegorz.eventapp.email.dto.MessageDto;

public interface EmailService {

    void sendEmail(MessageDto messageDto);
}