package co.id.telkom.digitalent.service;

import co.id.telkom.digitalent.dto.EmployeeDTO;
import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    // Uploads Folder in Current Path
    private final Path dirUploads = Paths.get("uploads");

    @Autowired
    private ModelMapper modelMapper;

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

    public Iterable<EmployeeModel> getAllEmployeeSortByName() {
        return employeeRepository.findAll(Sort.by(Order.asc("employeeName")));
    }

    public Page<EmployeeModel> getEmployeeByPage(int page, int size) {
        Pageable pagination = PageRequest.of(page, size);
        return employeeRepository.findAll(pagination);
    }

    public List<EmployeeDTO> getEmployeeByAddressAndName(String address, String name) {
        return employeeRepository.getEmployeeByAddressAndName(address, name).stream().map(this::convertToDTOMapper).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeeByAge(int age) {
        return employeeRepository.getEmployeeByAge(age).stream().map(this::convertToDTOMapper).collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(int id) {
        return employeeRepository.findById(id).map(this::convertToDTOMapper);
    }

    public Optional<EmployeeModel> getEmployeeByNameAndAddress(String name, String address) {
        return employeeRepository.getByEmployeeNameAndEmployeeAddress(name, address);
    }

    public Page<EmployeeModel> getEmployeeByName(String name, int page, int size) {
        Pageable pagination = PageRequest.of(page, size);
        return employeeRepository.getByEmployeeName(name, pagination);
    }

    public Page<EmployeeModel> getEmployeeByAddress(String address, int page, int size) {
        Pageable pagination = PageRequest.of(page, size);
        return employeeRepository.getByEmployeeAddress(address, pagination);
    }

    public EmployeeModel updateEmployee(EmployeeModel employeeModel, int id) {
        Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

        if (currentEmployee.isPresent()) {
            return employeeRepository.save(employeeModel);
        }

        return null;
    }

    public EmployeeModel patchEmployee(EmployeeModel employeeModel, int id) {
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

            return employeeRepository.save(employeeModel);
        }

        return null;
    }

    public EmployeeDTO deleteEmployee(int id) {
        Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);

        if (currentEmployee.isPresent()) {
            employeeRepository.deleteById(id);
            return currentEmployee.map(this::convertToDTOMapper).get();
        }

        return null;
    }

    public void deleteEmployeeByName(String name) {
        employeeRepository.deleteByEmployeeName(name);
    }

    public EmployeeModel uploadEmployeeFile(MultipartFile file, int id) {
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
                return employeeRepository.save(currEmployee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get File Extension
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
    private EmployeeDTO convertToDTOManual(EmployeeModel employeeModel) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setEmployeeName(employeeModel.getEmployeeName());
        employeeDTO.setEmployeeEmail(employeeModel.getEmployeeEmail());
        employeeDTO.setEmployeeAddress(employeeModel.getEmployeeAddress());
        employeeDTO.setEmployeeAge(employeeModel.getEmployeeAge());

        return employeeDTO;
    }

    // Convert To DTO with Model Mapper
    private EmployeeDTO convertToDTOMapper(EmployeeModel employeeModel) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        EmployeeDTO employeeDTO = modelMapper.map(employeeModel, EmployeeDTO.class);

        return employeeDTO;
    }
}
