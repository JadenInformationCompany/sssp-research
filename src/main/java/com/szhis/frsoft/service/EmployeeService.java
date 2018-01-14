package com.szhis.frsoft.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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

	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		String jpql = "select a from Employee a";
		TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
		List<Employee> aList = query.getResultList();
		return aList;
	}

}
