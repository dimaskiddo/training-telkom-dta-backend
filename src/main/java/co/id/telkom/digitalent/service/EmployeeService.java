package co.id.telkom.digitalent.service;

import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeModel createEmployeeModel(EmployeeModel employeeModel) {
        return employeeRepository.save(employeeModel);
    }

    public Iterable<EmployeeModel> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public EmployeeModel getEmployeeById(int id) {
        return employeeRepository.findById(id).get();
    }

    public EmployeeModel getEmployeeByName(String name) {
        return employeeRepository.findByEmployeeName(name);
    }

    public Iterable<EmployeeModel> getEmployeeByAddress(String address) {
        return employeeRepository.findByEmployeeAddress(address);
    }

    public EmployeeModel getEmployeeByNameAndAddress(String name, String address) {
        return employeeRepository.findByEmployeeNameAndEmployeeAddress(name, address);
    }

}
