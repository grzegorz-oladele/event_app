package pl.grzegorz.eventapp.email.message;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.email.message.MessageTemplate.CREATE_EMPLOYEE_MESSAGE_TEMPLATE;

@Component
@NoArgsConstructor(access = PRIVATE)
class MessageFactoryImpl implements MessageFactory {

    @Override
    public MessageDto getMessage(String to) {
        return createMessage.getMessage(to);
    }

    private final MessageCreator createMessage = to -> {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (className.equals("pl.grzegorz.eventapp.employees.EmployeeServiceImpl") &&
                methodName.equals("createEmployee")) {
            return createMessage(to, "Witaj w aplikacji eventowej", CREATE_EMPLOYEE_MESSAGE_TEMPLATE.getMessage());
        }
        return null;
    };

    private MessageDto createMessage(String to, String subject, String content) {
        return MessageDto.builder()
                .withTo(to)
                .withSubject(subject)
                .withContent(content)
                .build();
    }
}