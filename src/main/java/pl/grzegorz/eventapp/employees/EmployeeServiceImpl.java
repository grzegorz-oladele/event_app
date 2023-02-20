package pl.grzegorz.eventapp.employees;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.eventapp.exceptions.EntityException;
import pl.grzegorz.eventapp.exceptions.ParticipantException;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeEndOfWorkDto;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;
import pl.grzegorz.eventapp.employees.dto.simple_entity.EmployeeSimpleEntity;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static pl.grzegorz.eventapp.employees.EmployeeEntity.toEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found";
    private static final String EMPLOYEE_NOT_FOUND_LOG_ERROR_MESSAGE = "Employee using id -> {} not found";
    private static final String EMPLOYEE_RETURN_LIST_LOG_ERROR_MESSAGE = "Return list of employees counting {} records";

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeOutputDto> getAllParticipants() {
        List<EmployeeOutputDto> participants = employeeRepository.findAllBy();
        log.info(EMPLOYEE_RETURN_LIST_LOG_ERROR_MESSAGE, participants.size());
        return participants;
    }

    @Override
    public List<EmployeeOutputDto> getAllEmployedParticipants() {
        List<EmployeeOutputDto> employedParticipants = employeeRepository.findAllEmployedEmployees();
        log.info(EMPLOYEE_RETURN_LIST_LOG_ERROR_MESSAGE, employedParticipants.size());
        return employedParticipants;
    }

    @Override
    public EmployeeOutputDto getParticipantById(long employeeId) {
        return employeeRepository.findAllById(employeeId)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_LOG_ERROR_MESSAGE, employeeId);
                    throw new EntityException(EMPLOYEE_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public EmployeeSimpleEntity getWorkingEmployeeSimpleEntityById(long employeeId) {
        EmployeeSimpleEntity employeeSimpleEntity = getEmployeeSimpleEntity(employeeId);
        checkEmployeeWorkingAndThrowExceptionIfNot(employeeId, employeeSimpleEntity);
        return employeeSimpleEntity;
    }

    private EmployeeSimpleEntity getEmployeeSimpleEntity(long employeeId) {
        return employeeRepository.findSimpleEntityById(employeeId)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_LOG_ERROR_MESSAGE, employeeId);
                    throw new EntityException(EMPLOYEE_NOT_FOUND_MESSAGE);
                });
    }

    private void checkEmployeeWorkingAndThrowExceptionIfNot(long employeeId, EmployeeSimpleEntity employeeSimpleEntity) {
        if (employeeSimpleEntity.getIsEmployed().equals(FALSE)) {
            log.error("Employee using id -> {} is no longer working and cannot be enrolled in an event", employeeId);
            throw new ParticipantException("Employee is no longer working and cannot be enrolled in an event");
        }
    }

    @Override
    public void createParticipant(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = toEntity(employeeDto);
        employeeRepository.save(employeeEntity);
        log.info("Create new employee with id -> {}", employeeEntity.getId());
    }

    @Override
    public void editParticipant(long employeeId, EmployeeDto employeeDto) {
        checkExistsEmployeeAndThrowExceptionIfNot(employeeId);
        EmployeeEntity editedEmployee = toEntity(employeeDto);
        editedEmployee.setId(employeeId);
        employeeRepository.save(editedEmployee);
        log.info("Save edited employee with id -> {}", employeeId);
    }

    private void checkExistsEmployeeAndThrowExceptionIfNot(long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            log.error(EMPLOYEE_NOT_FOUND_LOG_ERROR_MESSAGE, employeeId);
            throw new EntityException(EMPLOYEE_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void setParticipantAsUnemployed(EmployeeEndOfWorkDto employeeEndOfWorkDto) {
        EmployeeEntity employee = getEmployeeEntityById(employeeEndOfWorkDto.getEmployeeId());
        employee.setIsEmployed(FALSE);
        employee.setDateOfEndingWork(LocalDate.parse(employeeEndOfWorkDto.getEndDateOfWork()));
        employeeRepository.save(employee);
        log.error("Marking employee with id -> {} as not working", employeeEndOfWorkDto.getEmployeeId());
    }

    private EmployeeEntity getEmployeeEntityById(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_LOG_ERROR_MESSAGE, employeeId);
                    throw new EntityException(EMPLOYEE_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public void removeParticipant(long employeeId) {
        employeeRepository.deleteById(employeeId);
        log.info("Permanent removal employee with id -> {} from database", employeeId);
    }
}