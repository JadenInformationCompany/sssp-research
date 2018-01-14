package com.szhis.frsoft.common.exception;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.szhis.frsoft.common.FRUtils;

public class ThrowException {
		
	/*
	public static void runtime(String code, Object[] args) {
		throw new RuntimeException(FRApplication.getMessage(code, args));
	}*/
	
	public static void runtime(final String code, Object... args) {
		throw new RuntimeException(FRUtils.getMessage(code, args));
	}	
	
	public static void entityExists(final String code, Object... args) {
		throw new EntityExistsException(FRUtils.getMessage(code, args));
	}
	
	public static void entityNotFound(final String code, Object... args) {
		throw new EntityNotFoundException(FRUtils.getMessage(code, args));
	}	
	
	public static void dataInputError(String... args ) { // 数据传入有误，用于一般都不会出现的错误的统一提示\
		if(args.length==0){
			throw new RuntimeException(FRUtils.getMessage("Data.Input.error"));
		}else{
			StringBuilder msg = new StringBuilder();
			for(String paramName: args){
				msg.append(paramName+",");
			}
			msg.deleteCharAt(msg.length()-1);
			runtime("Data.Input.errorList", msg.toString());
		}
	}
}
