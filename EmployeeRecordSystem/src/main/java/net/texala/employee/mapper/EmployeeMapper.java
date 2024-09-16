
package net.texala.employee.mapper;

import org.mapstruct.Mapper;

import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	Employee toEntity(EmployeeVo employeeVo);

}
