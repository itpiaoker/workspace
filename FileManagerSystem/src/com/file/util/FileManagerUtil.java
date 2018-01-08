//package com.file.util;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FilenameUtils;
//
//import com.file.model.FilePO;
//
//public class FileManagerUtil {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// File dir = new File(Constant.C_DIR);
//		// List<FilePO> recursiveDir = recursiveDir(dir,
//		// Constant.GEMERATED_DEPTH);
//		// for (FilePO FilePO : recursiveDir) {
//		// System.out.println(FilePO);
//		// }
//	}
//
//	static List<FilePO> list = new ArrayList<FilePO>();
//
//	public static List<FilePO> recursiveDir(File dir, int counter) {
//		if (dir == null)
//			return null;
//
//		FilePO filepo = new FilePO();
//		// if(list.size()==10){
//		// return list;
//		// }
//		if (dir.isDirectory()) {
//			counter++;
//			File[] listFiles = dir.listFiles();
//			if (listFiles == null)
//				return null;
//			for (File file : listFiles) {
//				recursiveDir(file, counter);
//			}
//		} else if (dir.isFile()) {
//			filepo.setId(Constant.GEMERATED_ID++);
//			filepo.setName(dir.getName());
//			filepo.setExt(FilenameUtils.getExtension(dir.getName()));
//			filepo.setPath(dir.getPath());
//			filepo.setSize((int) dir.length());
//			filepo.setParentPath(dir.getParent());
//			filepo.setDepth(counter);
//			filepo.setCreateDate(null);
//			filepo.setChangeDate(null);
//			filepo.setDriver(Constant.MOVEABLE_DRIVER_TWO);
//			System.out.println("FileManagerUtil.recursiveDir filepo===" + filepo);
//			list.add(filepo);
//		} else if (dir.isHidden()) {
//			return null;
//		}
//
//		return list;
//	}
//
//}
