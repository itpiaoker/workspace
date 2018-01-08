package cn.com.carenet.components.greenplum.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
public class ExecShell {
	// 会话
	private Session session = null;
	// 通道
	private ChannelExec openChannel = null;

	// 获取session
	public Session getSession(String host, String user, String pwd, int port) throws JSchException {

		JSch jsch = new JSch();
		session = jsch.getSession(user, host, port);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(pwd);
		session.connect(5000);

		return session;
	}

	// 获取通路
	public ChannelExec getChannel(Session session) throws JSchException {
		openChannel = (ChannelExec) session.openChannel("exec");
		return openChannel;
	}

	public void closed(Session session, ChannelExec openChannel) {
		if (openChannel != null && !openChannel.isClosed()) {
			openChannel.disconnect();
		}
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
	}
}
