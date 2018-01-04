package com.szhis.frsoft.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szhis.frsoft.entity.Employee;
import com.szhis.frsoft.service.DepartmentService;
import com.szhis.frsoft.service.EmployeeService;

@Controller
public class EmployeeHandler {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeService.delete(id);
		return "redirect:/emps";
	}

	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (id != null) {
			Employee employee = employeeService.get(id);
			employee.setDepartment(null);
			map.put("employee", employee);
		}
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.PUT)
	public String update(Employee employee) {
		employeeService.save(employee);
		return "redirect:/emps";
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		Employee employee = employeeService.get(id);
		map.put("employee", employee);
		map.put("departments", departmentService.getAll());
		return "emp/input";
	}

	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(@RequestBody Employee employee) {
		employeeService.save(employee);
		return "redirect:/emps";
	}

	@ResponseBody
	@RequestMapping(value = "/ajaxValidateLastName", method = RequestMethod.POST)
	public String validateLastName(@RequestParam(value = "lastName", required = true) String lastName) {
		Employee employee = employeeService.getByLastName(lastName);
		if (employee == null) {
			return "0";
		} else {
			return "1";
		}
	}

	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentService.getAll());
		map.put("employee", new Employee());
		return "emp/input";
	}

	@RequestMapping("/emps")
	public String list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr,
			Map<String, Object> map) {
		int pageNo = 1;

		try {
			pageNo = Integer.parseInt(pageNoStr);
			if (pageNo < 1) {
				pageNo = 1;
			}
		} catch (Exception e) {
		}

		Page<Employee> page = employeeService.getPage(pageNo, 5);
		map.put("page", page);

		return "emp/list";
	}
}
