package com.szhis.frsoft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class JDBCTest {

	/**
	 * @desc 测试jdbc连接数据库
	 * @author jaden.liu
	 * @throws Exception 
	 * @createTime 2018年1月13日 下午3:45:17 void
	 */
	@Test
	public void test_jdbc() throws Exception {
		String url = "jdbc:mysql://192.168.1.122:3306/db01";
		String username = "root";
		String userpass = "cindy1234!@#$";
		//1.加载驱动程序
		Class.forName("com.mysql.jdbc.Driver");
		//2.获得数据库链接
		Connection conn = DriverManager.getConnection(url, username, userpass);
		//3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from person");
		//4.处理数据库的返回结果(使用ResultSet类)
		while (rs.next()) {
			System.out.println(rs.getString("name") + " " + rs.getString("age"));
		}

		//关闭资源
		rs.close();
		st.close();
		conn.close();
	}

}
