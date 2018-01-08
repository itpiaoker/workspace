package cn.com.carenet.scheduler.mainTest;

import java.io.IOException;

import cn.com.carenet.scheduler.web.WorkFlowConfManager;

public class TestJson {

	public static void main(String[] args) throws IOException {
		JFileReader.setFilePath("E:\\tmp\\test1.txt");
		String jsonStr = JFileReader.getJsonStringFromFile();
		WorkFlowConfManager workFlowConfManager = new WorkFlowConfManager();
		workFlowConfManager.generatePorps(jsonStr,"104");

	}

}
