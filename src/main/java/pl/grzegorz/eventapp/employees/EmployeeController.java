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
        return employeeService.getAllEmployees();
    }

    @GetMapping("/working")
    List<EmployeeOutputDto> getAllWorkingEmployees() {
        return employeeService.getAllHiredEmployees();
    }

    @GetMapping("/{employeeId}")
    EmployeeOutputDto getEmployeeById(@PathVariable long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping
    void createEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.createEmployee(employeeDto);
    }

    @PatchMapping("/{employeeId}")
    void editEmployeeById(@PathVariable long employeeId, @RequestBody EmployeeDto employeeDto) {
        employeeService.editEmployee(employeeId, employeeDto);
    }

    @PatchMapping("/exemptions")
    void markEmployeeAsUnemployed(@RequestBody EmployeeEndOfWorkDto employeeEndOfWorkDto) {
        employeeService.setEmployeeAsUnemployed(employeeEndOfWorkDto);
    }

    @DeleteMapping("/{employeeId}")
    void permanentRemoveEmployee(@PathVariable long employeeId) {
        employeeService.removeEmployee(employeeId);
    }
}