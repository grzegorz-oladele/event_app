package pl.grzegorz.eventapp.participants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.grzegorz.eventapp.employees.EmployeeTestInitValue;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeInEventOutputDto;
import pl.grzegorz.eventapp.events.EventSimpleEntity;
import pl.grzegorz.eventapp.participants.dto.output.ParticipantOutputDto;

import java.util.List;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.eventapp.employees.EmployeeTestInitValue.getEmployeeSimpleEntity;

@NoArgsConstructor(access = PRIVATE)
public class ParticipantTestInitValue {

    static ParticipantEntity getParticipantEntity() {
        return ParticipantEntity.builder()
                .withId(1L)
                .withEmployee(getEmployeeSimpleEntity())
                .withEvent(new EventSimpleEntity(1L))
                .build();
    }

    public static List<ParticipantOutputDto> getParticipants() {
        ParticipantOutputDto firstParticipant = ParticipantTestOutputDto.builder()
                .withId(1L)
                .withEmployee(EmployeeTestInitValue.getFirstParticipant())
                .build();
        ParticipantOutputDto secondParticipant = ParticipantTestOutputDto.builder()
                .withId(2L)
                .withEmployee(EmployeeTestInitValue.getSecondParticipant())
                .build();
        return asList(firstParticipant, secondParticipant);
    }

    @Getter
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class ParticipantTestOutputDto implements ParticipantOutputDto {

        private Long id;
        private EmployeeInEventOutputDto employee;
    }
}
