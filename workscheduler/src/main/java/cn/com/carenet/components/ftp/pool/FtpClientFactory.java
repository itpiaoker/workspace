package cn.com.carenet.components.ftp.pool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * 
 * @author Sherard Lee
 * @since 04/JULY/2017
 */
public class FtpClientFactory extends BasePooledObjectFactory<FTPClient> {
	private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String encoding;

    public FtpClientFactory(String host,int port,String username,String password,String encoding){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.encoding = encoding;
    }

    public FTPClient create() throws Exception {
        FTPClient client = new FTPClient();
        client.connect(host,port);
        client.login(username,password);
        client.setControlEncoding(encoding);
        return client;
    }

    public PooledObject<FTPClient> wrap(FTPClient obj) {
        return new DefaultPooledObject<FTPClient>(obj);
    }

    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        ftpClient.logout();
        ftpClient.disconnect();
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient client = p.getObject();
        return client.isAvailable() && client.isConnected();
    }

    @Override
    public void activateObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient client = p.getObject();
        client.connect(host,port);
        client.login(username,password);
        client.setControlEncoding(encoding);
    }

    @Override
    public void passivateObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient client = p.getObject();
        client.logout();
        client.disconnect();
    }

    public String getHost(){
        return host;
    }
}
