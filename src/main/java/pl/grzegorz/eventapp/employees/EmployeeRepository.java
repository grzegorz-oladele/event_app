package pl.grzegorz.eventapp.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeOutputDto> findAllBy();

    @Query("SELECT e FROM EmployeeEntity e WHERE e.isEmployed IS TRUE")
    List<EmployeeOutputDto> findAllEmployedEmployees();

    Optional<EmployeeOutputDto> findAllById(long employeeId);

    @Query("SELECT e FROM EmployeeSimpleEntity e WHERE e.id = ?1")
    Optional<EmployeeSimpleEntity> findSimpleEntityById(long id);
}
