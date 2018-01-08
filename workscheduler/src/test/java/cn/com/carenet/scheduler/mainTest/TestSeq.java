package cn.com.carenet.scheduler.mainTest;

import cn.com.carenet.logging.mongo.MLogger;
import cn.com.carenet.logging.mongo.MLoggerFactory;
import cn.com.carenet.logging.mongo.MongoComponentStatus;
import cn.com.carenet.logging.mongo.MongoJDBC;
import cn.com.carenet.scheduler.utils.PropertiesReader;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.io.File;
import java.io.IOException;

public class TestSeq {
	final static Logger LOG = LoggerFactory.getLogger(TestSeq.class);

	public static void main(String[] args) throws IOException {
		System.setProperty(PropertiesReader.PROPERTIES_FILE,
				new File(new File(ClassLoader.getSystemResource("").getPath()).getParent(),
						"config/application.properties").toString());
		MLogger logger= MLoggerFactory.getLogger(TestSeq.class);
		logger.setWorkFlowID("test");
		logger.setWorkFlowType("工作流(Carenet® Tumbling Cloud™)");
		logger.info("info a message3");
		MongoCollection<Document> documents = MongoJDBC.read();
		FindIterable<Document> findIt = documents.find();
		MongoCursor<Document> cursor = findIt.iterator();
		while (cursor.hasNext()) {
			LOG.info(cursor.next().toJson());
		}
		MongoJDBC.closeMongo();
	}
	
	public static void testMongoComponent(){
		System.setProperty(PropertiesReader.PROPERTIES_FILE,
				new File(ClassLoader.getSystemResource("").getPath(), "../config/application.properties").toString());
		
		MongoComponentStatus.getConnection();
		MongoComponentStatus.updateSuccessStat("13", "window_112", "spark-core");
		MongoComponentStatus.updateSuccessStat("13", "window_1123", "spark-core");
		MongoComponentStatus.updateSuccessStat("13", "window_1125", "spark-core");
		MongoComponentStatus.updateSuccessStat("14", "window_2112", "spark-core");
		MongoComponentStatus.remove("13");
		MongoCollection<Document> documents = MongoComponentStatus.read();
		FindIterable<Document> findIt = documents.find();
		MongoCursor<Document> cursor = findIt.iterator();
		while (cursor.hasNext()) {
			LOG.info(cursor.next().toJson());
		}
		
//		MongoComponentStatus.updateRunningStat("12", "window_110", "ETL");
		
		MongoComponentStatus.closeMongo();
	}
}
