package com.szhis.frsoft.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.szhis.frsoft.common.exception.Exceptions;

public abstract class SpringBeanUtilsEx extends BeanUtils {

	/**
	 * 字段修改日志
	 * @author redhan by 2015年10月1日
	 *
	 */
	public static class fieldLog{
		private String name;
		private String oldValue;
		private String newValue;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOldValue() {
			return oldValue;
		}
		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}
		public String getNewValue() {
			return newValue;
		}
		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}
	}
	
	public static class fieldLogs{
		private String objectName;//对象名
		private String keyName; //主键
		private String keyValue;//主键值
		private List<fieldLog> logs; //字段日志列表
		

		public String getObjectName() {
			return objectName;
		}
		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}
		public String getKeyName() {
			return keyName;
		}
		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}
		public String getKeyValue() {
			return keyValue;
		}
		public void setKeyValue(String keyValue) {
			this.keyValue = keyValue;
		}
		public List<fieldLog> getLogs() {
			return logs;
		}
		public void setLogs(List<fieldLog> logs) {
			this.logs = logs;
		}
	}
 
	
	public static fieldLogs copyIgnoreNullPropertiesAndLog(Object source, Object target, String keyname, String... ignoreProperties){
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		fieldLogs result = new fieldLogs();
		result.setKeyName(keyname);
		PropertyDescriptor[] sourcePds = getPropertyDescriptors(source.getClass());
		ArrayList<String> ignoreNullProperties = new ArrayList<String>();
		fieldLog log = null;
		boolean changed;
		Class<?> propClass;		
		for (PropertyDescriptor sourcePd : sourcePds) {
			Method readMethod = sourcePd.getReadMethod();
			if (readMethod != null) {
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
					readMethod.setAccessible(true);				
				Object value;
				try {					
					value = readMethod.invoke(source);
					if (value == null){
						ignoreNullProperties.add(sourcePd.getName());
					} else {						
						if (result.getLogs() == null){
							result.setLogs(new ArrayList<fieldLog>());
						}
						
						Object oldValue = readMethod.invoke(target);
						propClass = value.getClass();
						if (propClass == String.class)  {
							if (((String)value) == "" && oldValue == null) {
								changed = false;
							} else {
								changed = !value.equals(oldValue);	
							}
						} else if (propClass == Integer.class || propClass == Date.class 
								|| propClass == Long.class) {
							changed = !value.equals(oldValue);
						} else if (propClass == BigDecimal.class) {
							changed = ((BigDecimal) value).compareTo((BigDecimal) oldValue) != 0;
						} else {
							changed = false;
						}
						if (changed) {
							log = new fieldLog();
							log.setName(sourcePd.getName());
							if (oldValue != null) {
								log.setOldValue(oldValue.toString());
							}
							log.setNewValue(value.toString());
							result.logs.add(log);
						}						
					}						
				} catch (Throwable ex) {
					throw Exceptions.unchecked(ex);
				}
			}
		}
		
		String[] ignorePropertiesMerge = new String[ignoreNullProperties.size() + ignoreProperties.length];		
		ignoreNullProperties.toArray(ignorePropertiesMerge);
		if(ignoreProperties.length > 0)
			System.arraycopy(ignoreProperties, 0, ignorePropertiesMerge, ignoreNullProperties.size(), ignoreProperties.length);
		
		BeanUtils.copyProperties(source, target, ignorePropertiesMerge);
		
		return result;
	}
	
	/**
	 * 
	 * @author xiaozheng
	 * @param source
	 * @param target
	 * @param ignoreNull 是否忽略null值属性
	 * @param properties 属性列表
	 */
	public static void copyPropertiesByProps(Object source, Object target, Boolean ignoreNull, String... properties) {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Assert.notEmpty(properties, "properties must not be empty");
		
		PropertyDescriptor sourcePd, targetPd;
		Method readMethod, writeMethod;
		for (String propName: properties) {
			sourcePd = getPropertyDescriptor(source.getClass(), propName);
			targetPd = getPropertyDescriptor(target.getClass(), propName);
			if (sourcePd != null && targetPd != null) {
				readMethod = sourcePd.getReadMethod();
				writeMethod = targetPd.getWriteMethod();
				if (writeMethod != null && readMethod != null 
						&& ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
					try {
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						
						Object value = readMethod.invoke(source);
						if ((value == null) && ignoreNull) {
							continue;
						}
						
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
					}					
				}
			}
		}		
	}
	
	/**
	 * 忽略源对象中值为null的属性和参数ignoreProperties指定忽略的的属性
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 */
	public static void copyIgnoreNullProperties(Object source, Object target, String... ignoreProperties) {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		PropertyDescriptor[] sourcePds = getPropertyDescriptors(source.getClass());
		ArrayList<String> ignoreNullProperties = new ArrayList<String>();
		
		for (PropertyDescriptor sourcePd : sourcePds) {
			Method readMethod = sourcePd.getReadMethod();
			if(readMethod != null) {
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
					readMethod.setAccessible(true);				
				Object value;
				try {
					value = readMethod.invoke(source);
					if (value == null)
						ignoreNullProperties.add(sourcePd.getName());					
				} catch (Throwable ex) {
					throw Exceptions.unchecked(ex);
				}
			}
		}
		
		String[] ignorePropertiesMerge = new String[ignoreNullProperties.size() + ignoreProperties.length];		
		ignoreNullProperties.toArray(ignorePropertiesMerge);
		if(ignoreProperties.length > 0)
			System.arraycopy(ignoreProperties, 0, ignorePropertiesMerge, ignoreNullProperties.size(), ignoreProperties.length);
		
		BeanUtils.copyProperties(source, target, ignorePropertiesMerge);
	}
}
