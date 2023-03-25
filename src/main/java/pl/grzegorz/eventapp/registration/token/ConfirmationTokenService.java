package pl.grzegorz.eventapp.registration.token;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken token);

    ConfirmationToken getToken(String token);

    int setConfirmedAt(String token);
}