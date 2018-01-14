package com.szhis.frsoft.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.szhis.frsoft.aop.anno.DataSourceExchange;
import com.szhis.frsoft.common.DataSourceContextHolder;

//@Component
@Aspect
public class DataSourceAdvice {

	/**
	 * @desc 拦截目标方法，获取由@DataSource指定的数据源标识，
	 * 设置到线程存储中以便切换数据源
	 * @author tomas.liu
	 * @createTime 2018年1月7日 下午10:35:08
	 * @param jp void
	 */
	@Before(value = "execution(* com.szhis.frsoft.repository.*.*(..))")
	public void beforeRunning(JoinPoint point) throws Throwable {
		Class<?> target = point.getTarget().getClass();
		MethodSignature signature = (MethodSignature) point.getSignature();
		// 默认使用目标类型的注解，如果没有则使用其实现接口的注解
		for (Class<?> clazz : target.getInterfaces()) {
			resolveDataSource(clazz, signature.getMethod());
		}
		resolveDataSource(target, signature.getMethod());
	}

	/**
	 * 提取目标对象方法注解和类型注解中的数据源标识
	 * 
	 * @param clazz
	 * @param method
	 */
	private void resolveDataSource(Class<?> clazz, Method method) {
		try {
			Class<?>[] types = method.getParameterTypes();
			// 放在类上-默认使用类型注解
			if (clazz.isAnnotationPresent(DataSourceExchange.class)) {
				DataSourceExchange source = clazz.getAnnotation(DataSourceExchange.class);
				DataSourceContextHolder.setDataSource(source.name());
			}
			// 放在方法上-方法注解可以覆盖类型注解
			Method m = clazz.getMethod(method.getName(), types);
			if (m != null && m.isAnnotationPresent(DataSourceExchange.class)) {
				DataSourceExchange source = m.getAnnotation(DataSourceExchange.class);
				DataSourceContextHolder.setDataSource(source.name());
			}
		} catch (Exception e) {
			System.out.println(clazz + ":" + e.getMessage());
		}
	}

	/**
	 * @desc 函数运行结束后释放连接
	 * @author tomas.liu
	 * @createTime 2018年1月14日 上午11:50:31
	 * @throws Throwable void
	 */
	@After(value = "execution(* com.szhis.frsoft.repository.*.*(..))")
	public void afterReturning() throws Throwable {
		DataSourceContextHolder.clearDataSource();
	}

}
