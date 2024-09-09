package net.texala.employee.service;

import java.util.List;
import java.util.Optional;

import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

public interface EmployeeService {

	public List<Employee> getAllEmployees();

	public Optional<Employee> getEmployeeById(int id);

	public EmployeeVo addEmployee(EmployeeVo employeeVo);

	public EmployeeVo updateEmployee(EmployeeVo employeeVo);

	public boolean deleteEmployee(int id);
}