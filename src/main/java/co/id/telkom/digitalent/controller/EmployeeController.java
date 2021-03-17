package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.dto.EmployeeDTO;
import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.response.GlobalResponse;
import co.id.telkom.digitalent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public GlobalResponse<Iterable<EmployeeModel>> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public GlobalResponse<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/name/{name}")
    public GlobalResponse<Iterable<EmployeeModel>> getEmployeeByName(@PathVariable String name) {
        return employeeService.getEmployeeByName(name);
    }

    @GetMapping("/address/{address}")
    public GlobalResponse<Iterable<EmployeeModel>> getEmployeeByAddress(@PathVariable String address) {
        return employeeService.getEmployeeByAddress(address);
    }

    @GetMapping("/findByNameAndAddress")
    public GlobalResponse<EmployeeDTO> getEmployeeByNameAndAddress(@RequestParam String name,
                                                                   @RequestParam String address) {
        return employeeService.getEmployeeByNameAndAddress(name, address);
    }

    @PutMapping("/{id}")
    public GlobalResponse<EmployeeModel> updateEmployee(@PathVariable int id,
                                                        @RequestBody EmployeeModel employeeModel) {
        return employeeService.updateEmployee(employeeModel, id);
    }

    @PatchMapping("/{id}")
    public GlobalResponse<EmployeeModel> patchEmployee(@PathVariable int id,
                                                       @RequestBody EmployeeModel employeeModel) {
        return employeeService.patchEmployee(employeeModel, id);
    }

    @DeleteMapping("/{id}")
    public GlobalResponse<EmployeeDTO> deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }

    @DeleteMapping("/name/{name}")
    public boolean deleteEmployeeByName(@PathVariable String name) {
        return employeeService.deleteEmployeeByName(name);
    }

    @PostMapping("/upload/{id}")
    public GlobalResponse<EmployeeModel> uploadEmployeeFile(@PathVariable int id,
                                            @RequestParam("file") MultipartFile file) {
        return employeeService.uploadEmployeeFile(file, id);
    }
}
