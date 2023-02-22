package pl.grzegorz.eventapp.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.email.message.MessageDto;

@Service
@RequiredArgsConstructor
@Slf4j
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(MessageDto messageDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("eventtest448@gmail.com");
        mailMessage.setTo("grzegorz.oladele@gmail.com");
        mailMessage.setText(messageDto.getContent());
        mailMessage.setSubject(messageDto.getSubject());
        javaMailSender.send(mailMessage);
        log.info("Mail sent successfully to -> {}", messageDto);
    }
}