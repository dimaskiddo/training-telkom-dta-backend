package co.id.telkom.digitalent.service;

import co.id.telkom.digitalent.model.EmployeeModel;
import co.id.telkom.digitalent.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JWTService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<EmployeeModel> employeeModel = employeeRepository.getByEmployeeEmail(email);

        if (employeeModel.isPresent()) {
            EmployeeModel currentEmployee = employeeModel.get();
            return new User(currentEmployee.getEmployeeEmail(), currentEmployee.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }
}
