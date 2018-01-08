package cn.com.carenet.components.localHdfs;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
/**
 * 上传下载
 * @author ljd
 *
 */
public class CopyLocalFileToHdfs{
	
	static Configuration coof =null;
	static URI uri  =null;
	static{
		try {
			coof = new Configuration();
			uri = new URI("hdfs://192.168.1.101:8020");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 本地文件上传到hdfs
	 * @param source 原文件路径
	 * @param dest 目的文件路径
	 * @return 
	 * @throws IOException 
	 */
	public static  void localFromToHDFS(String source,String dest) throws IOException{
		try {
			FileSystem fileSystem = FileSystem.get(uri, coof);
			System.err.println("连接成功");
			//原文件路径
			Path sourcePath = new Path(source);
			//目标文件路径
			Path destPath = new Path(dest);
			
			if(!(fileSystem.exists(destPath))){
				fileSystem.create(destPath);//如果目标文件不存在则就创建文件
			}
			fileSystem.copyFromLocalFile(sourcePath, destPath);
			System.out.println("上传成功");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			FileSystem.closeAll();
		}
	}
	/**  
	 * 从hdfs上下载文件到本地
	 * @param source 本地文件
	 * @param dest hdfs文件   
	 * @throws IOException
	 */
	public static  void hdfsFromToLocal(String source,String dest) throws IOException{
		FileSystem fs = FileSystem.get(uri, coof);   
		FSDataInputStream fssl = fs.open(new Path(dest));
		FileOutputStream fos = new FileOutputStream(source);
		IOUtils.copyBytes(fssl, fos, 4096,true);
	}
}