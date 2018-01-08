package cn.com.carenet.components.ftp.pool;

import java.io.Serializable;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
/**
 * 
 * @author Sherard Lee
 * @since 04/JULY/2017
 */
public class FtpClientPool extends GenericObjectPool<FTPClient> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3433166628492823811L;

	public FtpClientPool(){
        super(null);
        this.setMaxIdle(0);
        this.setMaxTotal(1);
        this.setTestOnBorrow(true);
        this.setTestWhileIdle(true);
    }

    public FtpClientPool(FtpClientFactory factory){
        super(factory);
    }
}
