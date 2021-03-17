package co.id.telkom.digitalent.response;

import co.id.telkom.digitalent.model.EmployeeModel;

public class EmployeeResponse extends BaseResponse {
    private EmployeeModel employeeModel;

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }
}
