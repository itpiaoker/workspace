//package cn.com.linnax.file.util;
//
//import cn.com.linnax.file.model.Fileinfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.util.List;
//
//public class FileScannerTest {
//
//	public final static Logger _log = LoggerFactory.getLogger("");
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) throws Exception {
//		long start = System.currentTimeMillis();
////		File dir = new File(Constant.C_DIR);
//		File dir = new File("C:\\wamp\\www\\wampthemes\\modern");
//		recursiveDir(dir, Constant.GEMERATED_DEPTH);
//		long end = System.currentTimeMillis();
//		_log.info("end - start \t" + (end - start));
//	}
//
//
//	public static List<Fileinfo> recursiveDir(File dir, int counter) throws Exception{
//		if (dir == null){
//			return null;
//		}
//
////		Fileinfo filepo = new Fileinfo();
//		// if(list.size()==10){
//		// return list;
//		// }
//		if (dir.isDirectory()) {
////			System.out.println(dir.getName());
////			$Recycle.Bin
//
//			if("$Recycle.Bin".equals(dir.getName())){
//				return null;
//			}
//
//			if("Program Files".equals(dir.getName())){
//				return null;
//			}
//
//			if("Program Files (x86)".equals(dir.getName())){
//				return null;
//			}
//
//			if("ProgramData".equals(dir.getName())){
//				return null;
//			}
//
//			counter++;
//
//			File[] listFiles = dir.listFiles();
//			if (listFiles == null)
//				return null;
//			for (File file : listFiles) {
//				recursiveDir(file, counter);
//			}
//
//		} else if (dir.isFile()) {
////			filepo.setId(Constant.GEMERATED_ID++);
////			filepo.setName(dir.getName());
////			filepo.setExt(FilenameUtils.getExtension(dir.getName()));
////			filepo.setPath(dir.getPath());
////			filepo.setSize((int) dir.length());
////			filepo.setParentPath(dir.getParent());
////			filepo.setDepth(counter);
////			filepo.setCreateDate(null);
////			filepo.setChangeDate(null);
//			Thread.sleep(5);
////			filepo.setDriver(Constant.MOVEABLE_DRIVER_TWO);
//			_log.info("线程启动并运行" + Thread.currentThread().getName() + "\t\t" +dir.getPath());
////			list.add(filepo);
//		} else if (dir.isHidden()) {
//			return null;
//		}
//
//		return null;
//	}
//
//}