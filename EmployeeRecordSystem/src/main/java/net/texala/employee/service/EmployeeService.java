package net.texala.employee.service;

import java.util.List;
import java.util.Optional;

import net.texala.employee.model.Employee;

public interface EmployeeService {
    
    public List<Employee> getAllEmployees();
    
    public Optional<Employee> getEmployeeById(int id);   
    
    public Employee addEmployee(Employee employee);      
    
    public Employee updateEmployee(Employee employee);   
    
    public boolean deleteEmployee(int id);               
}
