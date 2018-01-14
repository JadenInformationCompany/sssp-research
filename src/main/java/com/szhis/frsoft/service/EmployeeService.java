package com.szhis.frsoft.service;

import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szhis.frsoft.entity.Employee;

@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EntityManager em;

	public void saveBean(Employee emp) {
		Date now = new Date();
		emp.setCreateTime(now);
		em.persist(emp);
	}

}
