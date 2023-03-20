package pl.grzegorz.eventapp.employees;

import lombok.*;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDate.parse;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EmployeeTestInitValue {

    static List<EmployeeOutputDto> getAllEmployeeDtoOutputList() {
        EmployeeTestOutputDto firstEmployee = EmployeeTestOutputDto.builder()
                .withId(1L)
                .withName("Tomasz")
                .withSurname("Tomaszewski")
                .withEmail("tomasz@tomaszewski.pl")
                .withDepartment("DEVELOPER")
                .withDateOfStartingWork(parse("2022-01-01"))
                .withDateOfEndingWork(null)
                .withIsEmployed(TRUE)
                .build();

        EmployeeTestOutputDto secondEmployee = EmployeeTestOutputDto.builder()
                .withId(2L)
                .withName("Łukasz")
                .withSurname("Łukaszewski")
                .withEmail("lukasz@lukaszewski.pl")
                .withDepartment("QA")
                .withDateOfStartingWork(parse("2022-03-01"))
                .withDateOfEndingWork(parse("2022-09-30"))
                .withIsEmployed(FALSE)
                .build();

        EmployeeTestOutputDto thirdEmployee = EmployeeTestOutputDto.builder()
                .withId(3L)
                .withName("Bartosz")
                .withSurname("Bartoszewski")
                .withEmail("bartosz@bartoszewski.pl")
                .withDepartment("DEVOPS")
                .withDateOfStartingWork(parse("2022-05-01"))
                .withDateOfEndingWork(null)
                .withIsEmployed(TRUE)
                .build();

        EmployeeTestOutputDto fourthEmployee = EmployeeTestOutputDto.builder()
                .withId(4L)
                .withName("Michał")
                .withSurname("Michałowski")
                .withEmail("michal@michalowski.pl")
                .withDepartment("PROJECT-MANAGER")
                .withDateOfStartingWork(parse("2022-05-01"))
                .withDateOfEndingWork(parse("2022-11-30"))
                .withIsEmployed(FALSE)
                .build();
        return asList(firstEmployee, secondEmployee, thirdEmployee, fourthEmployee);
    }

    static List<EmployeeOutputDto> getAllHiredEmployees() {
        return asList(getAllEmployeeDtoOutputList().get(0), getAllEmployeeDtoOutputList().get(2));
    }

    static EmployeeEntity getFirstEmployeeEntity() {
        return EmployeeEntity.builder()
                .withId(1L)
                .withName("Tomasz")
                .withSurname("Tomaszewski")
                .withEmail("tomasz@tomaszewski.pl")
                .withDepartment("DEVELOPER")
                .withDateOfStartingWork(parse("2022-01-01"))
                .withDateOfEndingWork(null)
                .withIsEmployed(TRUE)
                .build();
    }

    static EmployeeEntity getSecondEmployeeEntity() {
        return EmployeeEntity.builder()
                .withId(2L)
                .withName("Michał")
                .withSurname("Michałowski")
                .withEmail("michal@michalowski.pl")
                .withDepartment("PROJECT-MANAGER")
                .withDateOfStartingWork(parse("2022-05-01"))
                .withDateOfEndingWork(null)
                .withIsEmployed(FALSE)
                .build();

    }

    static EmployeeDto getEmployeeDto() {
        return EmployeeDto.builder()
                .withName("Paweł")
                .withSurname("Pawłowski")
                .withEmail("paweł@pawłowski.pl")
                .withDepartment("DEVELOPER")
                .withDateOfStartingWork("2022-01-01")
                .build();
    }

    public static EmployeeSimpleEntity getEmployeeSimpleEntity() {
        return EmployeeSimpleEntity.builder()
                .withId(1L)
                .withName("Tomasz")
                .withSurname("Tomaszewski")
                .withEmail("tomasz@tomaszewski.pl")
                .withDepartment("DEVELOPER")
                .build();
    }

    public static EmployeeSimpleEntity getSecondEmployeeSimpleEntity() {
        return EmployeeSimpleEntity.builder()
                .withId(2L)
                .withName("Marcin")
                .withSurname("Marcinkowski")
                .withEmail("marcin@marcinkowski.pl")
                .withDepartment("ANALYST")
                .build();
    }

    public static EmployeeSimpleEntity getThirdEmployeeSimpleEntity() {
        return EmployeeSimpleEntity.builder()
                .withId(3L)
                .withName("Arkadiusz")
                .withSurname("Jakubowski")
                .withEmail("arkadiusz@jakubowski.pl")
                .withDepartment("ANALYST")
                .build();
    }

    public static EmployeeSimpleEntity getFourthEmployeeSimpleEntity() {
        return EmployeeSimpleEntity.builder()
                .withId(4L)
                .withName("Mateusz")
                .withSurname("Matuszczyk")
                .withEmail("mateusz@matuszczyk.pl")
                .withDepartment("ANALYST")
                .build();
    }

    public static EmployeeInEventOutputDto getMainOrganizer() {
        return EmployeeInEventTestDtoOutput.builder()
                .withId(1L)
                .withName("Tomasz")
                .withSurname("Tomaszewski")
                .withEmail("tomasz@tomaszewski.pl")
                .withDepartment("DEVELOPER")
                .build();
    }

    public static EmployeeInEventOutputDto getAssistantOutputDto() {
        return EmployeeInEventTestDtoOutput.builder()
                .withId(2L)
                .withName("Paweł")
                .withSurname("Pawłowski")
                .withEmail("pawel@pawlowski.pl")
                .withDepartment("DEVELOPER")
                .build();
    }

    public static EmployeeInEventOutputDto getFirstParticipant() {
        return EmployeeInEventTestDtoOutput.builder()
                .withId(3L)
                .withName("Bartosz")
                .withSurname("Bartoszewski")
                .withEmail("bartosz@bartoszewski.pl")
                .withDepartment("DEVOPS")
                .build();
    }

    public static EmployeeInEventOutputDto getSecondParticipant() {
        return EmployeeInEventTestDtoOutput.builder()
                .withId(4L)
                .withName("Michał")
                .withSurname("Michałowski")
                .withEmail("michal@michalowski.pl")
                .withDepartment("PROJECT-MANAGER")
                .build();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Builder(setterPrefix = "with")
    public static class EmployeeTestOutputDto implements EmployeeOutputDto {

        private Long id;
        private String name;
        private String surname;
        private String email;
        private String department;
        private LocalDate dateOfStartingWork;
        private LocalDate dateOfEndingWork;
        private Boolean isEmployed;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Builder(setterPrefix = "with")
    public static class EmployeeInEventTestDtoOutput implements EmployeeInEventOutputDto {

        private Long id;
        private String name;
        private String surname;
        private String email;
        private String department;
    }
}