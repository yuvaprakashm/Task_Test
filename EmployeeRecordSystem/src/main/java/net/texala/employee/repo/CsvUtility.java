package net.texala.employee.repo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.model.Employee;

public class CsvUtility {

	@Value("${file.path}")
	private String filePath;

	private static final String CSV_HEADER = "ID,Name,Email,Salary,DOB";

	private List<Employee> employeeCache = new ArrayList<>();

	private void loadEmployees() {
		employeeCache = readEmployeesFromCsv();
	}

	@PostConstruct
	public void init() {
		loadEmployees();
	}

	public List<Employee> findAll() {
		return new ArrayList<>(employeeCache);
	}

	public Optional<Employee> findById(Long id) {
		return employeeCache.stream().filter(emp -> emp.getId().equals(id)).findFirst();
	}

	public  void add(Employee employee) {
//		if (findById(employee.getId()).isPresent()) {
//			throw new ServiceException("Employee with ID '" + employee.getId() + "' already exists.");
//		}
//		if (findByEmail(employee.getEmail()).isPresent()) {
//			throw new ServiceException("Email '" + employee.getEmail() + "' already exists.");
//		}
		employeeCache.add(employee);
		writeToCsv(employeeCache);
	}

	public void update(Employee employee) {
		Optional<Employee> existingEmployeeOpt = findById(employee.getId());
		if (!existingEmployeeOpt.isPresent()) {
			throw new ServiceException("Employee with ID '" + employee.getId() + "' does not exist.");
		}
		Employee existingEmployee = existingEmployeeOpt.get();
		if (findByEmail(employee.getEmail()).isPresent() && !existingEmployee.getEmail().equals(employee.getEmail())) {
			throw new ServiceException("Email '" + employee.getEmail() + "' already exists.");
		}
		employeeCache.remove(existingEmployee);
		employeeCache.add(employee);
		writeToCsv(employeeCache);
	}

	public void delete(Long id) {
		Employee employeeToRemove = findById(id)
				.orElseThrow(() -> new ServiceException("Employee with ID '" + id + "' not found."));
		employeeCache.remove(employeeToRemove);
		writeToCsv(employeeCache);
	}

	public void save(Employee employee) {
		findById(employee.getId()).ifPresent(existingEmployee -> employeeCache.remove(existingEmployee));
		employeeCache.add(employee);
		writeToCsv(employeeCache);
	}

	public Optional<Employee> findByEmail(String email) {
		return employeeCache.stream().filter(emp -> emp.getEmail().equals(email)).findFirst();
	}

	private List<Employee> readEmployeesFromCsv() {
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("CSV file not found.");
			return Collections.emptyList();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			br.readLine();
			return br.lines().map(line -> line.split(",", -1)).map(data -> {
				try {
					Employee emp = new Employee();
					emp.setId(Long.parseLong(data[0]));
					emp.setName(data[1]);
					emp.setEmail(data[2]);
					emp.setSalary(Double.parseDouble(data[3]));
					emp.setDob(data[4]);
					return emp;
				} catch (NumberFormatException e) {
					System.err.println("Invalid number format in CSV data: " + e.getMessage());
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("Error in reading csv file: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	private void writeToCsv(List<Employee> employees) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			bw.write(CSV_HEADER);
			bw.newLine();
			for (Employee emp : employees) {
				bw.write(String.join(",", String.valueOf(emp.getId()), emp.getName(), emp.getEmail(),
						String.valueOf(emp.getSalary()), emp.getDob()));
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}

}