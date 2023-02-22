package pl.grzegorz.eventapp.email.message;

@FunctionalInterface
interface MessageCreator {

    MessageDto getMessage(String to);
}