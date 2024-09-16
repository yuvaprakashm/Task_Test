package net.texala.employee.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repo.EmployeeRepository;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee with ID '" + id + "' not found."));
	}

	@Override
	public EmployeeVo add(EmployeeVo employeeVo) {
		employeeRepository.add(employeeMapper.toEntity(employeeVo));
		return employeeVo;
	}

	@Override
	public EmployeeVo update(EmployeeVo employeeVo) {
		employeeRepository.update(employeeMapper.toEntity(employeeVo));
		return employeeVo;
	}

	@Override
	public boolean delete(Long id) {
		employeeRepository.delete(id);
		return true;
	}
}