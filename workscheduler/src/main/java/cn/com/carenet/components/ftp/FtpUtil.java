package cn.com.carenet.components.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carenet.components.ftp.pool.FtpClientFactory;
import cn.com.carenet.components.ftp.pool.FtpClientPool;
import cn.com.carenet.components.hadoop.HDFSUtil;

public class FtpUtil {
	final private static Logger LOG = LoggerFactory.getLogger(FtpUtil.class);
	private FtpClientPool ftpClientPool;
	
	public void prepare(String host,int port,String username,String password,String encoding){
		FtpClientFactory ftpClientFactory =new FtpClientFactory(host,port,username,password,encoding);
		ftpClientPool = new FtpClientPool(ftpClientFactory);
	}
	
	public void ftpToHdfs(String remoteFile,String hdfsPath) throws Exception{
		FTPClient ftpClient = ftpClientPool.borrowObject();
		InputStream ftpInputStream = ftpClient.retrieveFileStream(remoteFile);
		HDFSUtil.uploadToHdfs(ftpInputStream, hdfsPath);
		ftpInputStream.close();
		ftpClient.completePendingCommand();
		ftpClientPool.returnObject(ftpClient);
	}
	
	public void hdfsToFtp(String hdfsPath,String remoteFile) throws Exception{
		FTPClient ftpClient = ftpClientPool.borrowObject();
		OutputStream ftpOutputStream = ftpClient.storeFileStream(remoteFile);
		HDFSUtil.downLoadFromHdfs(hdfsPath, ftpOutputStream);
		ftpOutputStream.close();
		ftpClient.completePendingCommand();
		ftpClientPool.returnObject(ftpClient);
	}
	
	public void localToFtp(String localFile,String remotePath) throws Exception{
		FTPClient ftpClient = ftpClientPool.borrowObject();
		if(!ftpClient.changeWorkingDirectory(remotePath)){
            ftpClient.mkd(remotePath);
            ftpClient.changeWorkingDirectory(remotePath);
        }
		FileInputStream fileInputStream = new FileInputStream(new File(localFile));
		boolean ftpresult = ftpClient.storeFile(Paths.get(localFile).getFileName().toString(),fileInputStream);
		if(!ftpresult){
			LOG.error("Failed to upload {} to ftp: {}",localFile,remotePath);
		}
		fileInputStream.close();
		ftpClient.completePendingCommand();
    	ftpClientPool.returnObject(ftpClient);
	}
	public void ftpToLocal(String remoteFile,String localPath) throws Exception{
		FTPClient ftpClient = ftpClientPool.borrowObject();
		InputStream ftpInputStream = ftpClient.retrieveFileStream(remoteFile);
		File localFile = new File(Paths.get(localPath,Paths.get(remoteFile).getFileName().toString()).toString());
		FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
		byte[] bytes = new byte[4096];
		int c;
		while((c=ftpInputStream.read(bytes))!=-1){
			localFileOutputStream.write(bytes, 0, c);
		}
		localFileOutputStream.close();
		ftpInputStream.close();
		ftpClient.completePendingCommand();
    	ftpClientPool.returnObject(ftpClient);
	}
}
