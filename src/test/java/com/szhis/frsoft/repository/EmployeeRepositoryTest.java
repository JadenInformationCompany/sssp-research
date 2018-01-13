package com.szhis.frsoft.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.szhis.frsoft.BaseTest;
import com.szhis.frsoft.entity.Department;
import com.szhis.frsoft.entity.Employee;

public class EmployeeRepositoryTest extends BaseTest {
	//@Autowired
	private EmployeeRepository employeeRepository;
	//@Autowired
	private DepartmentRepository departmentRepository;

	@Test
	@Rollback(false)
	public void test_save() {
		List<Employee> aList = new ArrayList<Employee>();
		Department dept = departmentRepository.findOne(101);
		System.out.println(dept.getId());
		for (int i = 0; i < 1; i++) {
			Employee emp = new Employee();
			Date now = new Date();
			emp.setBirth(now);
			emp.setCreateTime(now);
			emp.setDepartment(dept);
			emp.setEmail("qq@qq.ccc");
			emp.setLastName("lisi");

			aList.add(emp);
		}

		List<Employee> save = employeeRepository.save(aList);
		System.out.println(save.size());
	}

	@Test
	public void testGetOne() {
		Employee findOne = employeeRepository.findOne(1);
		Employee findTow = employeeRepository.findOne(1);
		//以及缓存
		System.out.println(findOne);
		System.out.println(findTow);
		System.out.println(findOne == findTow);
	}

	/**
	 * @desc 二级缓存使用方式一
	 * @author jaden.liu
	 * @createTime 2017年12月15日 下午8:13:15 void
	 */
	@Test
	public void test_a1() {
		Employee one = employeeRepository.findById(102);
		Employee two = employeeRepository.findById(102);
		System.out.println(one == two);
		List<Employee> aList = employeeRepository.findAllCached();
		List<Employee> bList = employeeRepository.findAllCached();
		//false但是只发送了一条hql语句
		System.out.println(aList == bList);
	}

	@PersistenceContext
	private EntityManager em;

	/**
	 * @desc 二级缓存使用方式一
	 * @author jaden.liu
	 * @createTime 2017年12月15日 下午8:13:15 void
	 */
	@Test
	public void test_a2() {
		Query query = em.createQuery("select a from Employee a where a.id = 102");
		Employee emp = (Employee) query.setHint("org.hibernate.cacheable", true).getSingleResult();
		System.out.println(emp);
		Employee emp2 = (Employee) query.setHint("org.hibernate.cacheable", true).getSingleResult();
		System.out.println(emp == emp2);
	}

}
