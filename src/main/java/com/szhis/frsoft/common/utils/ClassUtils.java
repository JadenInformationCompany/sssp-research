package com.szhis.frsoft.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {
	
	// Introspector caches BeanInfo classes for better performance
	
	// clazz.getField(name) 只获取公用属性,getDeclaredField 获取私有属性.但都不能获取基类属性
    public static Field getDeclaredField(Class<?> clazz, String fieldName){  
        Field field;      
        while(clazz != Object.class) {
            try {
				field = clazz.getDeclaredField(fieldName);
				return field; 
			} catch (NoSuchFieldException e) {
			}
            clazz = clazz.getSuperclass();
        } 
        return null;  
    }
        
    public static boolean hasProperty(Class<?> clazz, String propName) {
		 return ClassUtils.getDeclaredField(clazz, propName) != null;	
	}
	
	public static List<String> getPropsName(Class<?> clazz) {
		List<String> propList = new ArrayList<String>();
		Field[] fields;
		int i;
		while(clazz != Object.class) {
			fields = clazz.getDeclaredFields();			
			for(i=0; i < fields.length; i++){
				propList.add(fields[i].getName());
			}
			clazz = clazz.getSuperclass();
		}
		return propList;
	}
	
	public static String getPropGetMethodName(String propName) {//根据属性名获取get方法名
		String getter;
    	if(propName.length() > 1) {
    		if(Character.isUpperCase(propName.charAt(1)))//如果低二个字母是大写，不转换
    			getter = "get" + propName;
    		else
    			getter = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
    	} else
    		getter = "get" + propName.toUpperCase();
    	return getter;
	}
	
	public static String getPropSetMethodName(String propName) {//根据属性名获取set方法名
		String setter;
    	if(propName.length() > 1) {
    		if(Character.isUpperCase(propName.charAt(1)))//如果低二个字母是大写，不转换
    			setter = "set" + propName;
    		else
    			setter = "set" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
    	} else
    		setter = "set" + propName.toUpperCase();
    	return setter;
	}	

}
