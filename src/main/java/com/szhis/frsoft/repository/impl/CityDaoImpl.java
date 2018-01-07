package com.szhis.frsoft.repository.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.szhis.frsoft.entity.City;
import com.szhis.frsoft.repository.ICityDao;

@Repository
public class CityDaoImpl implements ICityDao {

	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<City> find() {
		String hql = "from City";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
}