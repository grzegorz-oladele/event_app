package pl.grzegorz.eventapp.email.message;

public interface MessageFactory {

    MessageDto getMessage(String to);
}
