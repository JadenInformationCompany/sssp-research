package com.szhis.frsoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szhis.frsoft.entity.Department;
import com.szhis.frsoft.repository.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;

	@Transactional(readOnly = true)
	public List<Department> getAll() {
		return departmentRepository.getAll();
	}
}
