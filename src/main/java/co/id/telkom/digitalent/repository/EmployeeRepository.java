package co.id.telkom.digitalent.repository;

import co.id.telkom.digitalent.model.EmployeeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeModel, Integer> {

    Optional<EmployeeModel> getByEmployeeEmail(String email);

    Page<EmployeeModel> getByEmployeeName(String name, Pageable pagination);

    Page<EmployeeModel> getByEmployeeAddress(String address, Pageable pagination);

    Optional<EmployeeModel> getByEmployeeNameAndEmployeeAddress(String name, String address);

    @Transactional
    void deleteByEmployeeName(String name);

    // EmployeeModel is an Entity (JPQL)
    @Query("select e from EmployeeModel e where e.employeeAge < ?1")
    List<EmployeeModel> getEmployeeByAge(int age);

    // Native Query
    @Query(value = "select * from tb_employee where employee_address like %?1% " +
            "and employee_name like %?2%", nativeQuery = true)
    List<EmployeeModel> getEmployeeByAddressAndName(String address, String name);
}
