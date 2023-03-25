package pl.grzegorz.eventapp.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.grzegorz.eventapp.employees.dto.output.EmployeeOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeOutputDto> findAllBy();

    @Query("SELECT e FROM EmployeeEntity e WHERE e.isEmployed IS TRUE")
    List<EmployeeOutputDto> findAllEmployedEmployees();

    Optional<EmployeeOutputDto> findAllById(long employeeId);

    Optional<EmployeeEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE EmployeeEntity e SET e.enabled = TRUE WHERE e.email = ?1")
    int enableEmployeeEntity(String email);
}