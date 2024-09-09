package net.texala.employee.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String CSV_FILE = "D:/employees.csv";
	
	private static final String CSV_HEADER = "ID,Name, Email,       Salary,    DOB";


	@Override
	public List<Employee> getAllEmployees() {
		return readEmployeesFromCsv();
	}

	private List<Employee> readEmployeesFromCsv() {
		List<Employee> employees = new ArrayList<>();
		File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file not found.");
            return employees;
        }
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			String line;

			if ((line = br.readLine()) == null) {
				return employees;
			}
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 5) {
					try {
						Employee emp = new Employee();
						emp.setId(Integer.parseInt(data[0].trim()));
						emp.setName(data[1].trim());
						emp.setEmail(data[2].trim());
						emp.setSalary(Double.parseDouble(data[3].trim()));
						emp.setDob(data[4].trim());
						employees.add(emp);
					} catch (NumberFormatException e) {
						System.err.println("Invalid number format in CSV data: " + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error in reading csv file: " + e.getMessage());
		}
		return employees;
	}
 

	@Override
	public Optional<Employee> getEmployeeById(int id) {
	    for (Employee emp : readEmployeesFromCsv()) {
	        if (emp.getId() == id) {
	            return Optional.of(emp);
	        }
	    }
	    throw new NoSuchElementException("ID not found: " + id);
	}


	@Override
	public EmployeeVo addEmployee(EmployeeVo employeeVo) {
	    List<Employee> employees = readEmployeesFromCsv();
	    for (Employee emp : employees) {
	        if (emp.getId() == employeeVo.getId() || emp.getEmail().equals(employeeVo.getEmail())) {
	            throw new RuntimeException("Employee with ID or Email already exists.");
	        }
	    }
	    Employee newEmployee = new Employee();
	    newEmployee.setId(employeeVo.getId());
	    newEmployee.setName(employeeVo.getName());
	    newEmployee.setEmail(employeeVo.getEmail());
	    newEmployee.setSalary(employeeVo.getSalary());
	    newEmployee.setDob(employeeVo.getDob());

	    employees.add(newEmployee);
	    writeToCsv(employees);
	    return employeeVo;
	}


	@Override
	public EmployeeVo updateEmployee(EmployeeVo employeeVo) {
		List<Employee> employees = readEmployeesFromCsv();
		boolean found = false;
		for (int i = 0; i < employees.size(); i++) {
			Employee emp = employees.get(i);
			if (emp.getId() == employeeVo.getId()) {
				if (!emp.getEmail().equals(employeeVo.getEmail())) {
					for (Employee e : employees) {
						if (e.getEmail().equals(employeeVo.getEmail())) {
							throw new RuntimeException("Email already exists.");
						}
					}
				}
				emp.setName(employeeVo.getName());
				emp.setEmail(employeeVo.getEmail());
				emp.setSalary(employeeVo.getSalary());
				emp.setDob(employeeVo.getDob());
				found = true;
				break;
			}
		}
		if (found) {
			writeToCsv(employees);
			return employeeVo;
		}else {
			throw new RuntimeException("Employee not found.");

		}
		
	}

	@Override
	public boolean deleteEmployee(int id) {
		List<Employee> employees = readEmployeesFromCsv();
		boolean removed = false;
		for (int i = 0; i < employees.size(); i++) {
			Employee emp = employees.get(i);
			if (emp.getId() == id) {
				employees.remove(i);
				removed = true;
				break;
			}
		}
		if (removed) {
			writeToCsv(employees);
			return true;
		} else {
			throw new RuntimeException("Employee not found");
		}
	}

	private void writeToCsv(List<Employee> employees) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
			bw.write(CSV_HEADER);
			bw.newLine();
			for (Employee emp : employees) {
				String line = emp.getId() + "," + emp.getName() + "," + emp.getEmail() + "," + emp.getSalary() + "," + emp.getDob();
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error in writing file to CSV :" + e.getMessage());
		}

	}
}