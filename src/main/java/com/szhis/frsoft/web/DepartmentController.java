package com.szhis.frsoft.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.szhis.frsoft.entity.Department;
import com.szhis.frsoft.service.DepartmentService;

@Controller
@RequestMapping(value = { "/api/department" })
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = { RequestMethod.POST })
	public Department saveBean(@RequestBody Department dept) {
		departmentService.saveBean(dept);
		return dept;
	}

}
