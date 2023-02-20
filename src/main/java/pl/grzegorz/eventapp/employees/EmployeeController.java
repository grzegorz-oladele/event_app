package pl.grzegorz.eventapp.employees;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeEndOfWorkDto;
import pl.grzegorz.eventapp.employees.dto.input.EmployeeDto;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    List<EmployeeOutputDto> getAllEmployees() {
        return employeeService.getAllParticipants();
    }

    @GetMapping("/working")
    List<EmployeeOutputDto> getAllWorkingEmployees() {
        return employeeService.getAllEmployedParticipants();
    }

    @GetMapping("/{employeeId}")
    EmployeeOutputDto getEmployeeById(@PathVariable long employeeId) {
        return employeeService.getParticipantById(employeeId);
    }

    @PostMapping
    void createEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.createEmployee(employeeDto);
    }

    @PatchMapping("/{employeeId}")
    void editEmployeeById(@PathVariable long employeeId, @RequestBody EmployeeDto employeeDto) {
        employeeService.editParticipant(employeeId, employeeDto);
    }

    @PatchMapping("/exemptions")
    void markEmployeeAsUnemployed(@RequestBody EmployeeEndOfWorkDto employeeEndOfWorkDto) {
        employeeService.setParticipantAsUnemployed(employeeEndOfWorkDto);
    }

    @DeleteMapping("/{employeeId}")
    void permanentRemoveEmployee(@PathVariable long employeeId) {
        employeeService.removeParticipant(employeeId);
    }
}