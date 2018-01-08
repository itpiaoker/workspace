//package com.file.test;
//
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//
//import com.file.dao.AuthUserinfoDAO;
//import com.file.dao.AuthUserinfoDAOImpl;
//import com.file.model.AuthUserinfo;
//import com.file.util.SqlMapConf;
//
//public class UserDAOTest {
//
//	@Test
//	public void test() throws SQLException {
//		AuthUserinfoDAO dao=new AuthUserinfoDAOImpl(SqlMapConf.getSqlMapClient());
//		List<AuthUserinfo> list=dao.selectByExample(null);
//		System.out.println(list.size());
//	}
//	
//	@Test
//	public void test2() throws SQLException {
//		Date date=new Date();
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		String format2 = format.format(date);
//		System.out.println(format2);
//	}
//
//}
