package net.texala.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

	private Long id;
	private String name;
	private String email;
	private double salary;
	private String dob;

}