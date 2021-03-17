package co.id.telkom.digitalent.response;

import co.id.telkom.digitalent.model.EmployeeModel;

public class EmployeeAllResponse extends BaseResponse {

    private Iterable<EmployeeModel> employeeModels;

    public Iterable<EmployeeModel> getEmployeeModels() {
        return employeeModels;
    }

    public void setEmployeeModels(Iterable<EmployeeModel> employeeModels) {
        this.employeeModels = employeeModels;
    }
}
