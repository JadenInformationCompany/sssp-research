package com.szhis.frsoft.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szhis.frsoft.entity.Employee;
import com.szhis.frsoft.service.EmployeeService;

@Controller
@RequestMapping(value = { "/api/employee" })
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(method = { RequestMethod.POST })
	@ResponseBody
	public Employee saveBean(@RequestBody Employee emp) {
		employeeService.saveBean(emp);
		return emp;
	}

}
