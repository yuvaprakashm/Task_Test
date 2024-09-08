package net.texala.employee.service;

import java.util.List;

import net.texala.employee.model.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public Employee getEmployeeById(int id);
	
	public void addEmployee(Employee employee);
	
	public void updateEmployee(Employee employee);
	
	public void deleteEmployee(int id);
}
