package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/api/v1/employees", produces={"application/json"})
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public EmployeeModel createEmployee(@RequestBody EmployeeModel employeeModel) {
        return employeeService.createEmployeeModel(employeeModel);
    }

    @GetMapping
    public Iterable<EmployeeModel> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public EmployeeModel getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/name/{name}")
    public EmployeeModel getEmployeeByName(@PathVariable String name) {
        return employeeService.getEmployeeByName(name);
    }

    @GetMapping("/address/{address}")
    public Iterable<EmployeeModel> getEmployeeByAddress(@PathVariable String address) {
        return employeeService.getEmployeeByAddress(address);
    }

    @GetMapping("/findByNameAndAddress")
    public EmployeeModel getEmployeeByNameAndAddress(@RequestParam String name,
                                                     @RequestParam String address) {
        return employeeService.getEmployeeByNameAndAddress(name, address);
    }

}
