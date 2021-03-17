package co.id.telkom.digitalent.repository;

import co.id.telkom.digitalent.model.EmployeeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer> {

    Iterable<EmployeeModel> findByEmployeeName(String name);
    Iterable<EmployeeModel> findByEmployeeAddress(String address);
    Optional<EmployeeModel> findByEmployeeNameAndEmployeeAddress(String name, String address);

    @Transactional
    void deleteByEmployeeName(String name);

}
