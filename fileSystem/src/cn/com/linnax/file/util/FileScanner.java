//package cn.com.linnax.file.util;
//
//import cn.com.linnax.file.model.Fileinfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class FileScanner {
//
//	public final static Logger _log = LoggerFactory.getLogger("");
//	// 创建线程容器
//	public final static ExecutorService executorService = Executors.newCachedThreadPool();
////	public final static ExecutorService executorService = Executors.newFixedThreadPool(1000);
//	private static List<String> list = new ArrayList<>();
//	private static Connection conn;
//
//
//
//	static {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://127.0.0.1:3306/filesystem?characterEncoding=utf-8";
//			conn = DriverManager.getConnection(url, "root", "123456");
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) throws Exception {
////		recursiveDir(Constant.G_NAME_2_T_WEB, new File("D:\\finalcode"), Constant.GEMERATED_DEPTH);
//
//
////		recursiveDir(Constant.D_NAME, new File(Constant.D_DIR), Constant.GEMERATED_DEPTH);
////		recursiveDir(Constant.G_NAME_2_T_WEB, new File(Constant.G_2_T_WEB), Constant.GEMERATED_DEPTH);
////		recursiveDir(Constant.H_NAME_2_T_BIG_DATA, new File(Constant.H_2_T_BIG_DATA), Constant.GEMERATED_DEPTH);
////		recursiveDir(Constant.I_NAME_2_T_MATH, new File(Constant.I_2_T_MATH), Constant.GEMERATED_DEPTH);
//		recursiveDir(Constant.J_NAME_1_T_WEB, new File(Constant.J_1_T_WEB), Constant.GEMERATED_DEPTH);
//		executorService.shutdown();
//
//	}
//
//
//	public static List<Fileinfo> recursiveDir(String driverName, File dir, int counter) throws Exception{
//		if (dir == null){
//			return null;
//		}
//
//		final int cnt = counter;
//
//		if (dir.isDirectory()) {
//
//			counter++;
//
//			if(list.contains(dir.getName())){
//				return null;
//			}
//
//			list.add(dir.getName());
//
//			File[] listFiles = dir.listFiles(new MP3FileFilter());
//			if (listFiles == null)
//				return null;
//			for (File file : listFiles) {
//				if(counter % 2 == 0){
//					executorService.execute(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								recursiveDir(driverName, file, cnt);
//							} catch (Exception e){
//
//							}
//						}
//					});
//				} else {
//					recursiveDir(driverName, file, counter);
//				}
//			}
//		} else if (dir.isFile()) {
//			Thread.sleep(100);
////			_log.info(driverName + "\t" + dir.getParent() + "\t" + dir.getName());
//			insert(driverName, dir.getParent(), dir.getName());
//		} else if (dir.isHidden()) {
//			return null;
//		}
//
//		return null;
//	}
//
//	public static void insert(String driverName, String parentPath, String name) throws Exception {
//		String sql = "insert into fileinfo_copy (driver, parentPath, name) values (?, ?, ?)";
//		PreparedStatement psmt = conn.prepareStatement(sql);
//		psmt.setString(1, driverName);
//		psmt.setString(2, parentPath);
//		psmt.setString(3, name);
////		psmt.executeUpdate();
//		psmt.execute();
//	}
//
//
//
//
//
//}