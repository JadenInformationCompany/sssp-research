package com.szhis.frsoft.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.szhis.frsoft.aop.anno.DataSourceExchange;
import com.szhis.frsoft.common.DataSourceContextHolder;

@Component
@Aspect
public class DataSourceAdvice {

	/**
	 * @desc 描述该方法的功能
	 * @author tomas.liu
	 * @createTime 2018年1月7日 下午10:35:08
	 * @param jp void
	 */
	@Before(value = "execution(* com.szhis.frsoft.repository.**.*(..))")
	public void setdataSourceTwo(Method method, Object[] args, Object target) {
		System.out.println("two--------------two--------------two");
		if (method.isAnnotationPresent(DataSourceExchange.class)) {
			DataSourceExchange datasource = method.getAnnotation(DataSourceExchange.class);
			DataSourceContextHolder.setDataSource(datasource.name());
		} else {
			DataSourceContextHolder.setDataSource(DataSourceExchange.dataSourceOne);
		}
	}

	@After(value = "execution(* com.test.dao.*.*(..))")
	public void afterReturning() throws Throwable {
		DataSourceContextHolder.clearDataSource();
	}

}
