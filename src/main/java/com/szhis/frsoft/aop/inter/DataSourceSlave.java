package com.szhis.frsoft.aop.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc 指定使用slave数据库
 * @author tomas.liu
 * @createTime 2018年1月7日 下午11:05:02
 * @version v1.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceSlave {
	String value();
}
