package cn.com.carenet.scheduler.utils;

import java.io.File;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmdExecutor {
	final private static Logger LOG = LoggerFactory.getLogger(CmdExecutor.class);
	public boolean finished = false;

	public boolean execCommand(String commandStr, File fileEnv) throws Exception {
		int exitcode = -1;
		finished = false;
		try {
			Process commandProcess = Runtime.getRuntime().exec(commandStr, null, fileEnv);
			Scanner in = new Scanner(commandProcess.getInputStream());
			Scanner inerr = new Scanner(commandProcess.getErrorStream());
			
			Thread shellPrinter = new Thread(new Runnable() {
				@Override
				public void run() {
					while (in.hasNext()) {
						String line = in.nextLine();
						LOG.debug(line);
						if (finished) {
							in.close();
							break;
						}
					}

				}

			});
			shellPrinter.setName("Shell-printer");

			Thread shellErrPrinter = new Thread(new Runnable() {
				@Override
				public void run() {
					while (inerr.hasNext()) {
						String line = inerr.nextLine();
						LOG.debug(line);
						if (finished) {
							inerr.close();
							break;
						}
					}

				}

			});
			shellErrPrinter.setName("Shell-error-printer");
			if(LOG.isDebugEnabled()){
				shellPrinter.start();
				shellErrPrinter.start();
			}
			exitcode = commandProcess.waitFor();
			finished = true;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception(e);
		}
		if (exitcode == 0) {
			return true;
		} else {
			return false;
		}
	}
}
