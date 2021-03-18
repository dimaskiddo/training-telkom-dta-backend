package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.dto.EmployeeDTO;
import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.response.SuccessResponse;
import co.id.telkom.digitalent.response.WriteResponse;
import co.id.telkom.digitalent.response.DataResponse;
import co.id.telkom.digitalent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping(value="/api/v1/employees", produces={"application/json"})
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public void createEmployee(HttpServletRequest request, HttpServletResponse response,
                               @RequestBody EmployeeModel employeeModel) throws IOException {
        DataResponse<EmployeeModel> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_CREATED);
        dataResponse.setData(employeeService.createEmployeeModel(employeeModel));

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping
    public void getAllEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataResponse<Iterable<EmployeeModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getAllEmployee());

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/sort/name")
    public void getAllEmployeeSortByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataResponse<Iterable<EmployeeModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getAllEmployeeSortByName());

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/pagination")
    public void getEmployeeByPage(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(name = "page", required = false) Integer page,
                                  @RequestParam(name = "size", required = false) Integer size) throws IOException {
        // Validate Page Number
        if (page == null) {
            page = 0;
        }

        // Validate Page Size
        if (size == null) {
            size = 10;
        }

        DataResponse<Page<EmployeeModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getEmployeeByPage(page, size));

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/query")
    public void getEmployeeByAddressAndName(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("address") String address,
                                            @RequestParam("name") String name) throws IOException {
        DataResponse<List<EmployeeDTO>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getEmployeeByAddressAndName(address, name));

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/query/age")
    public void getEmployeeByAge(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("age") int age) throws IOException {
        DataResponse<List<EmployeeDTO>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getEmployeeByAge(age));

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getEmployeeById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id) throws IOException {
        Optional<EmployeeDTO> currEmployee = employeeService.getEmployeeById(id);
        DataResponse<EmployeeDTO> dataResponse = new DataResponse<>();

        if (currEmployee.isPresent()) {
            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(currEmployee.get());

            WriteResponse.responseSuccessWithData(response, dataResponse);
        } else {
            WriteResponse.responseNotFound(response, "EMPLOYEE NOT FOUND");
        }
    }

    @GetMapping("/find")
    public void getEmployeeByNameAndAddress(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam String name,
                                            @RequestParam String address) throws IOException {
        Optional<EmployeeModel> currEmployee = employeeService.getEmployeeByNameAndAddress(name, address);
        DataResponse<EmployeeModel> dataResponse = new DataResponse<>();

        if (currEmployee.isPresent()) {
            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(currEmployee.get());

            WriteResponse.responseSuccessWithData(response, dataResponse);
        } else {
            WriteResponse.responseNotFound(response, "EMPLOYEE NOT FOUND");
        }
    }

    @GetMapping("/find/name/{name}")
    public void getEmployeeByName(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable String name,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size) throws IOException {
        // Validate Page Number
        if (page == null) {
            page = 0;
        }

        // Validate Page Size
        if (size == null) {
            size = 10;
        }

        DataResponse<Page<EmployeeModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getEmployeeByName(name, page, size));

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/find/address/{address}")
    public void getEmployeeByAddress(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable String address,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size) throws IOException {
        // Validate Page Number
        if (page == null) {
            page = 0;
        }

        // Validate Page Size
        if (size == null) {
            size = 10;
        }

        DataResponse<Page<EmployeeModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(employeeService.getEmployeeByAddress(address, page, size));

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @PutMapping("/{id}")
    public void updateEmployee(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id,
                               @RequestBody EmployeeModel employeeModel) throws IOException {
        EmployeeModel updatedEmployee = employeeService.updateEmployee(employeeModel, id);
        DataResponse<EmployeeModel> dataResponse = new DataResponse<>();

        if (updatedEmployee != null) {
            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(updatedEmployee);

            WriteResponse.responseSuccessWithData(response, dataResponse);
        } else {
            WriteResponse.responseNotFound(response, "EMPLOYEE NOT FOUND");
        }
    }

    @PatchMapping("/{id}")
    public void patchEmployee(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable int id,
                              @RequestBody EmployeeModel employeeModel) throws IOException {
        EmployeeModel patchedEmployee = employeeService.patchEmployee(employeeModel, id);
        DataResponse<EmployeeModel> dataResponse = new DataResponse<>();

        if (patchedEmployee != null) {
            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(patchedEmployee);

            WriteResponse.responseSuccessWithData(response, dataResponse);
        } else {
            WriteResponse.responseNotFound(response, "EMPLOYEE NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id) throws IOException {
        EmployeeDTO deletedEmployee = employeeService.deleteEmployee(id);
        DataResponse<EmployeeDTO> dataResponse = new DataResponse<>();

        if (deletedEmployee != null) {
            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(deletedEmployee);

            WriteResponse.responseSuccessWithData(response, dataResponse);
        } else {
            WriteResponse.responseNotFound(response, "EMPLOYEE NOT FOUND");
        }
    }

    @DeleteMapping("/name/{name}")
    public void deleteEmployeeByName(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable String name) throws IOException {
        employeeService.deleteEmployeeByName(name);
        WriteResponse.responseSuccessOK(response, "DELETE EMPLOYEE BY NAME SUCCESS");
    }

    @PostMapping("/upload/{id}")
    public void uploadEmployeeFile(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable int id,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        EmployeeModel uploadedEmployeeFile = employeeService.uploadEmployeeFile(file, id);
        DataResponse<EmployeeModel> dataResponse = new DataResponse<>();

        if (uploadedEmployeeFile != null) {
            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(uploadedEmployeeFile);

            WriteResponse.responseSuccessWithData(response, dataResponse);
        } else {
            WriteResponse.responseNotFound(response, "EMPLOYEE NOT FOUND");
        }
    }
}
