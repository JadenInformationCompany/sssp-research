package com.szhis.frsoft.repository;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.szhis.frsoft.BaseTest;

public class ConnectionDaoTest extends BaseTest {
	public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataSource ds;

	/**
	 * @desc 测试是否可以拿到数据源
	 * @author jaden.liu
	 * @createTime 2017年12月10日 上午11:23:47
	 * @throws SQLException void
	 */
	@Test
	public void test_con() throws SQLException {
		System.out.println("---------------------------------------------");
		System.out.println(ds);
		System.out.println(ds.getConnection());
	}

	/**
	 * @desc 日志输出位置测试
	 * @author jaden.liu
	 * @createTime 2017年12月10日 上午11:41:50 void
	 */
	@Test
	public void test_log() {
		LOGGER.debug("你好--------------------------debug");
		LOGGER.info("你好--------------------------info");
		LOGGER.error("你好--------------------------error");
	}

}
