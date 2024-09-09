package net.texala.employee.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@RestController 
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
		return ResponseEntity.ok(employeeService.getEmployeeById(id).orElse(null));
	}

	@PostMapping
	public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeVo employeeVo) {
	    employeeService.addEmployee(employeeVo);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeVo employeeVo) {
	    employeeVo.setId(id);   
	    employeeService.updateEmployee(employeeVo);
	    return ResponseEntity.ok("Employee updated successfully");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok("Employee deleted successfully");
	}
}