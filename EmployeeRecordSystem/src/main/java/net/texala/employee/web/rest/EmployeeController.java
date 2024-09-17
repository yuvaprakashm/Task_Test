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
	public ResponseEntity<List<Employee>> findAll() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> findById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.findById(id));
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody EmployeeVo employeeVo) {
	    employeeService.add(employeeVo);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody EmployeeVo employeeVo) {
	    employeeVo.setId(id);   
	    employeeService.update(employeeVo);
	    return ResponseEntity.ok("Employee updated successfully");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		employeeService.delete(id);
		return ResponseEntity.ok("Employee deleted successfully");
	}
}