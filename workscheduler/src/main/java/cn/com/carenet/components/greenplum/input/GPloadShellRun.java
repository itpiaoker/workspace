package cn.com.carenet.components.greenplum.input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

import cn.com.carenet.components.greenplum.GreenPlumControl;
import cn.com.carenet.components.greenplum.utils.ExecShell;


public class GPloadShellRun implements InputInterface {

	ExecShell conn = new ExecShell();

	public boolean execomm(String cmd) {
		Session session;
		ChannelExec channelExce;

		try {
			session = conn.getSession(GreenPlumControl.ssh_ip, GreenPlumControl.ssh_user, GreenPlumControl.ssh_pwd, 22);
			channelExce = conn.getChannel(session);
			channelExce.setCommand(cmd);
			channelExce.connect();

			InputStream in = channelExce.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String buf = null;
			String result = "";
			while ((buf = reader.readLine()) != null) {
				result += buf + "\n \t";
			}

			System.out.println("return====" + result);

			conn.closed(session, channelExce);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
