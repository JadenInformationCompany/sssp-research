package com.szhis.frsoft.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.szhis.frsoft.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee getByLastName(String lastName);

	/**
	 * 第一种情况的缓存,处理spring data jpa自身的方法
	 * @param id
	 * @return
	 */
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
	public Employee findById(Integer id);

	/**
	 * 第二种情况缓存,处理能根据 spring data jpa 扩展的方法.
	 * @return
	 */
	@Query("from Employee")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
	List<Employee> findAllCached();
}
