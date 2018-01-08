package cn.com.carenet.components.greenplum.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

@Service
public class GpConnect {

	public Statement st;

	/**
	 * args[0]=host,args[1]=port,args[2]=DatabaseName,args[3]=user,
	 * args[4]=Password
	 * 
	 * @param args
	 * @return GP_connect or error String
	 */
	public Statement getGPDBst(String[] args) {

		// host检测
		if (args[0].equals(""))
			return null;
		// database检测
		if (args[1].equals(""))
			return null;
		// user检测
		if (args[2].equals(""))
			return null;
		// Passed检测
		if (args[3].equals(""))
			return null;

		// 创建连接
		System.out.format("jdbc:pivotal:greenplum:\\\\%s;DatabaseName=%s,%s,%s \n", args[0], args[1], args[2],
				args[4]);
		try {
			Class.forName("com.pivotal.jdbc.GreenplumDriver");
			Connection db = DriverManager.getConnection(
					"jdbc:pivotal:greenplum://" + args[0] + ";DatabaseName=" + args[1], args[2],
					args[4]);

			st = db.createStatement();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return st;
	}
}
