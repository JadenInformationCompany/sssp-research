package com.szhis.frsoft.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @desc spring在获取数据源时会调用AbstractRoutingDataSource接口的实现类
 * @author jaden.liu
 * @createTime 2018年1月13日 下午3:51:21
 * @version v1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * spring预留接口，
	 * 用于动态切换数据源
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSource();
	}

}
