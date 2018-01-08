package cn.com.carenet.logging.mongo;

import cn.com.carenet.logging.LogWritter;

public class MongoWritter implements LogWritter {
	public MongoWritter() {
		MongoJDBC.getConnection();
	}

	@Override
	public void writeLine(String message, String workFlowID, String typeName) {
		MongoJDBC mongo = new MongoJDBC();
		mongo.setTypeName(typeName);
		mongo.setWorkFlowID(workFlowID);
		mongo.write(message);
	}
}
