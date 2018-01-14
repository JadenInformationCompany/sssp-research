package com.szhis.frsoft.service;

import javax.persistence.EntityManager;

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

}
