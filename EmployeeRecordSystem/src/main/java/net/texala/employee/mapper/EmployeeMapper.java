package net.texala.employee.mapper;

import org.springframework.stereotype.Component;

import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Component
public class EmployeeMapper {

    public Employee convertToEntity(EmployeeVo employeeVo) {
        Employee employee = new Employee();
        employee.setId(employeeVo.getId());
        employee.setName(employeeVo.getName());
        employee.setEmail(employeeVo.getEmail());
        employee.setSalary(employeeVo.getSalary());
        employee.setDob(employeeVo.getDob());
        return employee;
    }
}
