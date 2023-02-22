package pl.grzegorz.eventapp.email;

import pl.grzegorz.eventapp.email.message.MessageDto;

public interface EmailService {

    void sendEmail(MessageDto messageDto);
}