package cn.com.carenet.scheduler.mainTest;

import java.io.File;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransposeTest {
	final private static Logger LOG = LoggerFactory.getLogger(TransposeTest.class);
	private static boolean finished = false;
	private static File fileEnv;

	public static void main(String[] args) {
		StringBuffer cmd = new StringBuffer("cmd /c E:\\pagefile\\test.bat");
		LOG.info(cmd.toString());
		execCommand(cmd.toString());
	}

	protected static boolean execCommand(String commandStr) {
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
						System.out.println(line);
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
						System.out.println(line);
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
			return false;
		}
		if (exitcode == 0) {
			return true;
		} else {
			return false;
		}
	}
}
