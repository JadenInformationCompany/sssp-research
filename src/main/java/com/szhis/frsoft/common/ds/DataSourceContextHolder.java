package com.szhis.frsoft.common.ds;

/**
 * @desc 设置数据源类型key
 * @author jaden.liu
 * @createTime 2018年1月13日 下午3:58:22
 * @version v1.0
 */
public class DataSourceContextHolder {
	/**
	 * 注意：数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
	 */
	private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();

	/**
	 * @desc AbstractRoutingDataSource的实现类调用该方法
	 * @author jaden.liu
	 * @createTime 2018年1月13日 下午3:54:25
	 * @return String
	 */
	public static String getDataSource() {
		return THREAD_DATA_SOURCE.get();
	}

	/**
	 * @desc 使用aop设置
	 * @author jaden.liu
	 * @createTime 2018年1月13日 下午3:54:06
	 * @param customerType void
	 */
	public static void setDataSource(String dataSource) {
		THREAD_DATA_SOURCE.set(dataSource);
	}

	/**
	 * @desc aop调用该方法
	 * @author jaden.liu
	 * @createTime 2018年1月13日 下午3:54:46 void
	 */
	public static void clearDataSource() {
		THREAD_DATA_SOURCE.remove();
	}

}
