package com.szhis.frsoft.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szhis.frsoft.entity.Employee;
import com.szhis.frsoft.service.EmployeeService;

@Controller
public class JsonController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = { "/Employee/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Employee getEntity(@PathVariable("id") Integer id) {
		Employee employee = employeeService.get(id);
		return employee;
	}
}
