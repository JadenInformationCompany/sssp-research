package com.szhis.frsoft.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.MethodInvoker;

import com.szhis.frsoft.common.exception.Exceptions;

@Component
public class SpringApplicationContext implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
	
	private static ApplicationContext applicationContext;
	private static List<MethodInvoker> methodList = new ArrayList<MethodInvoker>();
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 设置当前上下文环境，此方法由spring自动装配
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		/*System.getProperties().list(System.out);*/
		applicationContext = arg0;
	}
	
	/**
	 * 当spring容器初始化完成后就会执行该方法,if (event.getApplicationContext().getParent() == null) 避免执行两次。
	 * 后续发现加上以上判断还是能执行两次，不加的话三次，最终研究结果使用以下判断更加准确：
	 * event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")
	 */
	@Override  
    public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!(event instanceof ContextRefreshedEvent)) {
			return;
		}
    	if (event.getApplicationContext().getParent() == null) { 
			try {
				for (MethodInvoker method: methodList) {
					method.prepare();
					method.invoke();
				}
			} catch (Exception e) {
				throw Exceptions.unchecked(e);
			} 
			methodList.clear();
			methodList = null;
    	}    	
    }
	
	/**
	 * 根据beanName 获取 spring bean
	 * @param beanName
	 * @return Object
	 */
	public static Object getBean(String beanName){
		if (beanName == null) {
			return null;
		}
	    return 	applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据bean type 获取springBean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBeanByType(Class<T> requiredType){
		return applicationContext.getBean(requiredType);
	}
	
	public static <T> T getBean(Class<T> requiredType) {		
		return applicationContext.getBean(requiredType);
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return applicationContext.getBeansOfType(type);	
	}
	/**
	 * 获取 Spring applicationContext
	 * @return
	 */
	public static ApplicationContext getContext() {
		return applicationContext;
	}	
	
	public static Object registerBean(Class<?> beanClass, String beanName) { // 注册Bean
		return registerBean(beanClass.getClass().getName(), beanName);
	}
	
	public static Object registerBean(String beanClassName, String beanName) { // 注册Bean
		BeanDefinition bean = new GenericBeanDefinition();
		bean.setBeanClassName(beanClassName);
		DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
		fty.registerBeanDefinition(beanName, bean);	
		return applicationContext.getBean(beanName);
	}	
	
	public static void registerInitMethod(MethodInvoker method) {
		methodList.add(method);
	}	
}
