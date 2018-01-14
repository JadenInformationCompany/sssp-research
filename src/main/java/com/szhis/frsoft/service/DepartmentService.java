package com.szhis.frsoft.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szhis.frsoft.entity.Department;

@Service
@Transactional
public class DepartmentService {
	@Autowired
	private EntityManager em;

	public void saveBean(Department dept) {
		em.persist(dept);
	}

	public List<Department> findAll() {
		String jpql = "select a from Department a";
		TypedQuery<Department> query = em.createQuery(jpql, Department.class);
		List<Department> aList = query.getResultList();
		return aList;
	}

}
