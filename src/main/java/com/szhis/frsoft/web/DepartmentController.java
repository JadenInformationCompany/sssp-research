package com.szhis.frsoft.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szhis.frsoft.entity.Department;
import com.szhis.frsoft.service.DepartmentService;

@Controller
@RequestMapping(value = { "/api/department" })
@ResponseBody
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = { RequestMethod.POST })
	public Department saveBean(@RequestBody Department dept) {
		departmentService.saveBean(dept);
		return dept;
	}

	@RequestMapping(method = { RequestMethod.GET })
	public List<Department> findAll() {
		List<Department> aList = departmentService.findAll();
		return aList;
	}

}
