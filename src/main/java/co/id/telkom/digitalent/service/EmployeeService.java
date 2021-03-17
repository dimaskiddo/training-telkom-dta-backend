package co.id.telkom.digitalent.service;

import co.id.telkom.digitalent.dto.EmployeeDTO;
import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.repository.EmployeeRepository;
import co.id.telkom.digitalent.response.GlobalResponse;
import co.id.telkom.digitalent.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    // Uploads Folder in Current Path
    private final Path dirUploads = Paths.get("uploads");

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeModel createEmployeeModel(EmployeeModel employeeModel) {
        return employeeRepository.save(employeeModel);
    }

    public GlobalResponse<Iterable<EmployeeModel>> getAllEmployee() {
        GlobalResponse<Iterable<EmployeeModel>> employeeAllResponse = new GlobalResponse<>();

        employeeAllResponse.setStatus(ResponseMessage.SUCCESS);
        employeeAllResponse.setMessage(HttpStatus.OK);
        employeeAllResponse.setData(employeeRepository.findAll());

        return employeeAllResponse;
    }

    public GlobalResponse<EmployeeDTO> getEmployeeById(int id) {
        GlobalResponse<EmployeeDTO> employeeResponse = new GlobalResponse<>();
        Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

        if (currentEmployee.isPresent()) {
            employeeResponse.setStatus(ResponseMessage.SUCCESS);
            employeeResponse.setMessage(HttpStatus.OK);
            employeeResponse.setData(currentEmployee.map(this::convertToDTO).get());
        } else {
            employeeResponse.setStatus(ResponseMessage.FAILED);
            employeeResponse.setMessage(HttpStatus.NOT_FOUND);
        }

        return employeeResponse;
    }

    public GlobalResponse<Iterable<EmployeeModel>> getEmployeeByName(String name) {
        GlobalResponse<Iterable<EmployeeModel>> employeeResponse = new GlobalResponse<>();

        employeeResponse.setStatus(ResponseMessage.SUCCESS);
        employeeResponse.setMessage(HttpStatus.OK);
        employeeResponse.setData(employeeRepository.findByEmployeeName(name));

        return employeeResponse;
    }

    public GlobalResponse<Iterable<EmployeeModel>> getEmployeeByAddress(String address) {
        GlobalResponse<Iterable<EmployeeModel>> employeeResponse = new GlobalResponse<>();

        employeeResponse.setStatus(ResponseMessage.SUCCESS);
        employeeResponse.setMessage(HttpStatus.OK);
        employeeResponse.setData(employeeRepository.findByEmployeeAddress(address));

        return employeeResponse;
    }

    public GlobalResponse<EmployeeDTO> getEmployeeByNameAndAddress(String name, String address) {
        GlobalResponse<EmployeeDTO> employeeResponse = new GlobalResponse<>();
        Optional<EmployeeModel> currentEmployee = employeeRepository.findByEmployeeNameAndEmployeeAddress(name, address);

        if (currentEmployee.isPresent()) {
            employeeResponse.setStatus(ResponseMessage.SUCCESS);
            employeeResponse.setMessage(HttpStatus.OK);
            employeeResponse.setData(currentEmployee.map(this::convertToDTO).get());
        } else {
            employeeResponse.setStatus(ResponseMessage.FAILED);
            employeeResponse.setMessage(HttpStatus.NOT_FOUND);
        }

        return employeeResponse;
    }

    public GlobalResponse<EmployeeModel> updateEmployee(EmployeeModel employeeModel, int id) {
        GlobalResponse<EmployeeModel> employeeResponse = new GlobalResponse<>();
        Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

        if (currentEmployee.isPresent()) {
            employeeResponse.setStatus(ResponseMessage.SUCCESS);
            employeeResponse.setMessage(HttpStatus.OK);
            employeeResponse.setData(employeeRepository.save(employeeModel));
        } else {
            employeeResponse.setStatus(ResponseMessage.FAILED);
            employeeResponse.setMessage(HttpStatus.NOT_FOUND);
        }

        return employeeResponse;
    }

    public GlobalResponse<EmployeeModel> patchEmployee(EmployeeModel employeeModel, int id) {
        GlobalResponse<EmployeeModel> employeeResponse = new GlobalResponse<>();
        Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

        if (currentEmployee.isPresent()) {
            EmployeeModel currEmployee = currentEmployee.get();
            if (employeeModel.getEmployeeName() != null &&
                !employeeModel.getEmployeeName().isEmpty()) {
                currEmployee.setEmployeeName(employeeModel.getEmployeeName());
            }

            if (employeeModel.getEmployeeEmail() != null &&
                !employeeModel.getEmployeeName().isEmpty()) {
                currEmployee.setEmployeeEmail(employeeModel.getEmployeeEmail());
            }

            if (employeeModel.getEmployeeAddress() != null &&
                !employeeModel.getEmployeeAddress().isEmpty()) {
                currEmployee.setEmployeeAddress(employeeModel.getEmployeeAddress());
            }

            if (employeeModel.getEmployeeAge() != 0) {
                currEmployee.setEmployeeAge(employeeModel.getEmployeeAge());
            }

            employeeResponse.setStatus(ResponseMessage.SUCCESS);
            employeeResponse.setMessage(HttpStatus.OK);
            employeeResponse.setData(employeeRepository.save(employeeModel));
        } else {
            employeeResponse.setStatus(ResponseMessage.FAILED);
            employeeResponse.setMessage(HttpStatus.NOT_FOUND);
        }

        return employeeResponse;
    }

    public GlobalResponse<EmployeeDTO> deleteEmployee(int id) {
        GlobalResponse<EmployeeDTO> employeeResponse = new GlobalResponse<>();
        Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

        if (currentEmployee.isPresent()) {
            employeeRepository.deleteById(id);

            employeeResponse.setStatus(ResponseMessage.SUCCESS);
            employeeResponse.setMessage(HttpStatus.OK);
            employeeResponse.setData(currentEmployee.map(this::convertToDTO).get());
        } else {
            employeeResponse.setStatus(ResponseMessage.FAILED);
            employeeResponse.setMessage(HttpStatus.NOT_FOUND);
        }

        return employeeResponse;
    }

    public boolean deleteEmployeeByName(String name) {
        employeeRepository.deleteByEmployeeName(name);
        return true;
    }

    public GlobalResponse<EmployeeModel> uploadEmployeeFile(MultipartFile file, int id) {
        GlobalResponse<EmployeeModel> employeeResponse = new GlobalResponse<>();

        try {
            Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

            if (currentEmployee.isPresent()) {
                EmployeeModel currEmployee = currentEmployee.get();

                if (!Files.exists(dirUploads)) {
                    Files.createDirectories(dirUploads);
                }

                String fileName = currEmployee.getEmployeeName() + getFileExtension(file.getOriginalFilename());
                Files.copy(file.getInputStream(), this.dirUploads.resolve(fileName));

                currEmployee.setEmployeeImage(fileName);

                employeeResponse.setStatus(ResponseMessage.SUCCESS);
                employeeResponse.setMessage(HttpStatus.OK);
                employeeResponse.setData(employeeRepository.save(currEmployee));
            } else {
                employeeResponse.setStatus(ResponseMessage.FAILED);
                employeeResponse.setMessage(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            employeeResponse.setStatus(e.getMessage());
            employeeResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return employeeResponse;
    }

    private String getFileExtension(String filename) {
        String extension = "";
        String[] arr = filename.split("\\.");

        if(arr.length > 0) {
            extension = arr[arr.length - 1];
            return "." + extension;
        } else {
            return extension;
        }
    }

    // Convert To DTO Manually
    private EmployeeDTO convertToDTO(EmployeeModel employeeModel) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setEmployeeName(employeeModel.getEmployeeName());
        employeeDTO.setEmployeeEmail(employeeModel.getEmployeeEmail());
        employeeDTO.setEmployeeAddress(employeeModel.getEmployeeAddress());
        employeeDTO.setEmployeeAge(employeeModel.getEmployeeAge());

        return employeeDTO;
    }
}
