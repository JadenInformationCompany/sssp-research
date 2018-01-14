package com.szhis.frsoft;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//spring Junit配置
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
//默认使用事务
@Transactional(transactionManager = "transactionManager")
//默认事务回滚
@Rollback(true)
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Override
	//@Resource(name = "dataSourceOne")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * @desc 添加maven打包时不出现test错误日志提示
	 * @author jaden.liu
	 * @createTime 2017年12月10日 上午11:19:22 void
	 */
	@Test
	public void maven_test() {
		System.out.println("--------------------------------junit run...maven required");
	}

}
