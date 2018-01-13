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
	 * 当前使用的dataSource
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getDSType();
	}

}
