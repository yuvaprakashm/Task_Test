package net.texala.employee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repo.CsvUtility;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private CsvUtility csvUtility;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public List<Employee> findAll() {
		return csvUtility.findAll();
	}

	@Override
	public Employee findById(Long id) {
		return csvUtility.findById(id)
				.orElseThrow(() -> new ServiceException("Employee with ID '" + id + "' not found."));
	}

	@Override
	public EmployeeVo add(EmployeeVo employeeVo) {
		csvUtility.add(employeeMapper.toEntity(employeeVo));
		return employeeVo;
	}

	@Override
	public EmployeeVo update(EmployeeVo employeeVo) {
		csvUtility.update(employeeMapper.toEntity(employeeVo));
		return employeeVo;
	}

	@Override
	public boolean delete(Long id) {
		csvUtility.delete(id);
		return true;
	}
}