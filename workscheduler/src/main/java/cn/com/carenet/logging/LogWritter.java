package cn.com.carenet.logging;

public interface LogWritter {
	void writeLine(String message, String workFlowID, String typeName);
}
