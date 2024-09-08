package net.texala.employee.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVo {

	private int id;

	@NotBlank(message = "Name is Required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only characters")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	private String email;

	@NotNull(message = "Salary must not be null")
	@Min(value = 0, message = "Salary must be non-negative")
	private int salary;

	@NotNull(message = "Date of birth is required")
	@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$", message = "Dob must be in valid format(dd-mm-yyyy)")
	private String dob;
}
