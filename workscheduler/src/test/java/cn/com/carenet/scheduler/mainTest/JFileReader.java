package cn.com.carenet.scheduler.mainTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JFileReader {
	private static String filePath;
	
	/**
	 * get the json string from a json file.
	 * @return
	 * @throws IOException
	 */
	public static String getJsonStringFromFile() throws IOException {
		
		File file=new File(filePath);
		FileInputStream fileIn = new FileInputStream(file);
		InputStreamReader fileReader = new InputStreamReader(fileIn);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String text = new String();
		String linetxt = null;
		while((linetxt = bufferedReader.readLine()) != null){
			text=text+linetxt;
        }
		bufferedReader.close();
		fileReader.close();
		fileIn.close();
        return text.trim();
	}
	public String getFilePath() {
		return filePath;
	}
	public static void setFilePath(String filePath) {
		JFileReader.filePath = filePath;
	}
}
