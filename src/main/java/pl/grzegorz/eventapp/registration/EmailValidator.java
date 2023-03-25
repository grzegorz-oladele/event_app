package pl.grzegorz.eventapp.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return true;
    }
}