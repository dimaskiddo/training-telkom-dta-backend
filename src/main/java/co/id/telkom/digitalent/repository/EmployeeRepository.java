package co.id.telkom.digitalent.repository;

import co.id.telkom.digitalent.model.EmployeeModel;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer> {

    EmployeeModel findByEmployeeName(String name);
    Iterable<EmployeeModel> findByEmployeeAddress(String address);
    EmployeeModel findByEmployeeNameAndEmployeeAddress(String name, String address);

}
