package cn.com.carenet.logging.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.com.carenet.scheduler.utils.DbConfigLoader;

public class MongoJDBC {
	private static String logHost = "192.168.1.104";
	private static String logPort = "27017";
	private static String logUser;
	private static String logPasswd;
	private static String logDb = "test";
	private static String logTable = "data";
	private static String logBufferTable = "datatmp";
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	
	private String typeName = "工作流(Carenet® Tumbling Cloud™)";
	private String workFlowID = "test";
	
	static {
		logHost = DbConfigLoader.getLogHost();
		logPort = DbConfigLoader.getLogPort();
		logUser = DbConfigLoader.getLogUser();
		logPasswd = DbConfigLoader.getLogPasswd();
		logDb = DbConfigLoader.getLogDb();
		logTable = DbConfigLoader.getLogTable();
		logBufferTable = DbConfigLoader.getLogBufferTable();
	}

	public static void getConnection() {
		ServerAddress serverAddress = new ServerAddress(logHost, Integer.parseInt(logPort));
		List<ServerAddress> addrs = new ArrayList<>();
		addrs.add(serverAddress);
		if (logUser != null) {
			MongoCredential credential = MongoCredential.createCredential(logUser, logDb, logPasswd.toCharArray());
			List<MongoCredential> credentials = new ArrayList<>();
			credentials.add(credential);
			mongoClient = new MongoClient(addrs, credentials);
		} else {
			mongoClient = new MongoClient(addrs);
		}
		mongoDatabase = mongoClient.getDatabase(logDb);
	}

	public void write(String line) {
		Document document = new Document("workFlowID", workFlowID).append("typeName", typeName)
				.append("dateTime", new Date().getTime()).append("infos", line);
		MongoCollection<Document> collectionBuffer = mongoDatabase.getCollection(logBufferTable);
		collectionBuffer.insertOne(document);
		MongoCollection<Document> collection = mongoDatabase.getCollection(logTable);
		collection.insertOne(document);
	}

	public static MongoCollection<Document> read() {
		MongoCollection<Document> collection = mongoDatabase.getCollection(logTable);
		return collection;
	}

	public static void closeMongo() {
		mongoClient.close();
	}

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
