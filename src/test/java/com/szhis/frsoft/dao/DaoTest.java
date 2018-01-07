package com.szhis.frsoft.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.szhis.frsoft.BaseTest;
import com.szhis.frsoft.entity.Brand;
import com.szhis.frsoft.entity.City;
import com.szhis.frsoft.repository.IBrandDao;
import com.szhis.frsoft.repository.ICityDao;

public class DaoTest extends BaseTest {

	@Resource
	private IBrandDao brandDao;

	@Resource
	private ICityDao cityDao;

	@Test
	public void test_a() {
		List<Brand> brands = brandDao.findAll();
		System.out.println(brands.size());
	}

	@Test
	public void testList() {
		List<Brand> brands = brandDao.findAll();
		System.out.println(brands.size());

		List<City> cities = cityDao.find();
		System.out.println(cities.size());
	}
}
