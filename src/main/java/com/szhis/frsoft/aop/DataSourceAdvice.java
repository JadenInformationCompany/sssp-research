package com.szhis.frsoft.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.szhis.frsoft.common.DatabaseContextHolder;

@Component
@Aspect
public class DataSourceAdvice {

	/**
	 * @desc 前置增强 在该方法执行之前先执行我的aop代码
	 * @author tomas.liu
	 * @createTime 2018年1月7日 下午10:33:34
	 * @param jp
	 */
	@Before(value = "execution(* com.szhis.frsoft.repository.impl.BrandDaoImpl.*(..))")
	public void setdataSourceOne(JoinPoint jp) {
		DatabaseContextHolder.setDataSource("dataSourceOne");
	}

	/**
	 * @desc 描述该方法的功能
	 * @author tomas.liu
	 * @createTime 2018年1月7日 下午10:35:08
	 * @param jp void
	 */
	@Before(value = "execution(* com.szhis.frsoft.repository.impl.CityDaoImpl.*(..))")
	public void setdataSourceTwo(JoinPoint jp) {
		System.out.println("ssss");
		DatabaseContextHolder.setDataSource("dataSourceTwo");
	}
}
