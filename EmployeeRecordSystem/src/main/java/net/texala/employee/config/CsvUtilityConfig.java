package net.texala.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.texala.employee.repo.CsvUtility;

@Configuration
public class CsvUtilityConfig {

	@Bean
	public CsvUtility myCsvUtility() {
		return new CsvUtility();
	}
}
