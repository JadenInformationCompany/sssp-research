package com.szhis.frsoft;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.szhis.frsoft.entity.JavaPojo;
import com.szhis.frsoft.repository.TransactionTestDao;

public class TransactionTest extends BaseTest {
	//@Autowired
	private TransactionTestDao transactionTestDao;

	/**
	 * @desc 不允许在此插入数据，
	 * 当maven打包时会运行此代码
	 * @author jaden.liu
	 * @createTime 2017年12月15日 下午7:16:58 void
	 */
	@Test
	@Rollback(true)
	public void test_trans() {
		JavaPojo jp = new JavaPojo();
		jp.setName("zhangsan");
		JavaPojo save = transactionTestDao.save(jp);
		System.out.println(save);
	}

}
