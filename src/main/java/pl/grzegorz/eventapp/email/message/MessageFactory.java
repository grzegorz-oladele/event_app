package pl.grzegorz.eventapp.email.message;

import lombok.NoArgsConstructor;
import pl.grzegorz.eventapp.email.dto.MessageDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MessageFactory {

    private static final String CREATE_EMPLOYEE_MESSAGE_TEMPLATE = "Zarejestrowano w aplikacji z użyciem maila %-1s";

    public static MessageDto messageFactory(String to) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (className.equals("pl.grzegorz.eventapp.employees.EmployeeServiceImpl") &&
                methodName.equals("createEmployee")) {
            return createMessage(to, "Witaj w aplikacji eventowej", CREATE_EMPLOYEE_MESSAGE_TEMPLATE);
        }
        return null;
    }

    private static MessageDto createMessage(String to, String subject, String content) {
        return MessageDto.builder()
                .withTo(to)
                .withSubject(subject)
                .withContent(content)
                .build();
    }
}