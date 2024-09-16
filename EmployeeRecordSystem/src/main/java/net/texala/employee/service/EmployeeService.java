package net.texala.employee.service;

import java.util.List;

import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

public interface EmployeeService {

	public List<Employee> findAll();

	public Employee findById(Long id);

	public EmployeeVo add(EmployeeVo employeeVo);

	public EmployeeVo update(EmployeeVo employeeVo);

	public boolean delete(Long id);

}