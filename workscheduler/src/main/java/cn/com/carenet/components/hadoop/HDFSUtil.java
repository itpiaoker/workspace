package cn.com.carenet.components.hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carenet.scheduler.utils.PropertiesReader;

public class HDFSUtil {
	final private static Logger LOG = LoggerFactory.getLogger(HDFSUtil.class);
	private static Configuration hdfsConfig = PropertiesReader.getHdfsConfig();
	public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
	public static final String USER_NAME_KEY = "hdfs.kerberos.principal";

	public HDFSUtil() {
		if (hdfsConfig.get(KEYTAB_FILE_KEY) != null)
			try {
				SecurityUtil.login(hdfsConfig, KEYTAB_FILE_KEY, USER_NAME_KEY);
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
	}

	/**
	 * this method does not close the input stream.
	 * 
	 * @param in
	 * @param filePath
	 * @throws IOException
	 */
	public static void uploadToHdfs(InputStream in, String filePath) throws IOException {
		FileSystem fs = FileSystem.get(hdfsConfig);
		Path path = new Path(filePath);
		FSDataOutputStream out = fs.create(path);
		IOUtils.copyBytes(in, out, 4096, true);
		out.flush();
		out.close();
		fs.close();
	}

	/**
	 * this method does not close the output stream.
	 * 
	 * @param filePath
	 * @param out
	 * @throws IOException
	 */
	public static void downLoadFromHdfs(String filePath, OutputStream out) throws IOException {
		FileSystem fs = FileSystem.get(hdfsConfig);
		Path fileName = new Path(filePath);
		FSDataInputStream in = fs.open(fileName);
		IOUtils.copyBytes(in, out, 4096, true);
		in.close();
		out.flush();
		fs.close();
	}

	public static boolean createFilePath(String filePath) throws IOException {
		FileSystem fs = null;
		fs = FileSystem.get(hdfsConfig);
		Path dfs = new Path(filePath);
		boolean isMkdirs = fs.mkdirs(dfs);
		fs.close();
		return isMkdirs;
	}

	public static void createFile(String fileName, String message) throws IOException {
		FileSystem fs = FileSystem.get(hdfsConfig);
		Path path = new Path(fileName);
		FSDataOutputStream out = fs.create(path);
		out.writeUTF(message);
		out.flush();
		out.close();
		fs.close();
	}

	public static boolean deleteFile(String filePath) {
		FileSystem fs = null;
		try {
			fs = FileSystem.get(hdfsConfig);
			Path fileName = new Path(filePath);
			return fs.delete(fileName, true);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
		return false;
	}

	public static boolean renameFile(String oldName, String newName) {
		FileSystem fs = null;
		try {
			fs = FileSystem.get(hdfsConfig);
			Path oldPath = new Path(oldName);
			Path newPath = new Path(newName);
			return fs.rename(oldPath, newPath);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
		return false;
	}

	public static boolean fileExist(String filePath) {
		FileSystem fs = null;
		try {
			fs = FileSystem.get(hdfsConfig);
			Path fileName = new Path(filePath);
			return fs.exists(fileName);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
		return false;
	}

	public static void fileUpdateTime(String filePath) {
		FileSystem fs = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			fs = FileSystem.get(hdfsConfig);
			Path fileName = new Path(filePath);
			FileStatus status = fs.getFileStatus(fileName);
			System.out.println("+ status.getLen()");
			System.out.println("ʱ" + sdf.format(new Date(status.getModificationTime())));
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	public static void fileList(String filePath) {
		FileSystem fs = null;
		try {
			fs = FileSystem.get(hdfsConfig);
			Path fileName = new Path(filePath);
			FileStatus[] status = fs.listStatus(fileName);
			Path[] path = FileUtil.stat2Paths(status);
			for (Path file : path) {
				System.out.println("" + file.getName());
				System.out.println("·" + file);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

}
