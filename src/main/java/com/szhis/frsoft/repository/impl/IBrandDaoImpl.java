package com.szhis.frsoft.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.szhis.frsoft.aop.anno.DataSourceExchange;
import com.szhis.frsoft.entity.Brand;
import com.szhis.frsoft.repository.IBrandDao;

@Repository("iBrandDao")
public class IBrandDaoImpl implements IBrandDao {

	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@DataSourceExchange(name = "dataSourceOne")
	public List<Brand> findAll() {
		String hql = "from Brand";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
}
