package cn.com.carenet.logging.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import cn.com.carenet.scheduler.utils.DbConfigLoader;

public class MongoComponentStatus {
	final static int STAT_NONE = 0;
	final static int STAT_START = 1;
	final static int STAT_SUCCESS = 2;
	final static int STAT_FAILED = 3;

	private static String logHost = "192.168.1.104";
	private static String logPort = "27017";
	private static String logUser;
	private static String logPasswd;
	private static String logDb = "test";
	private static String statusTable = "WF_STAT";
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	static {
		logHost = DbConfigLoader.getLogHost();
		logPort = DbConfigLoader.getLogPort();
		logUser = DbConfigLoader.getLogUser();
		logPasswd = DbConfigLoader.getLogPasswd();
		logDb = DbConfigLoader.getLogDb();
		statusTable = DbConfigLoader.getStatusTable();
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

	public static void insertNoneStat(String workFlowID, String componentID, String typeName) {
		Document document = new Document("workFlowID", workFlowID).append("componentID", componentID)
				.append("typeName", typeName).append("status", 0).append("startTime", 0).append("endTime", 0);
		MongoCollection<Document> collection = mongoDatabase.getCollection(statusTable);
		collection.insertOne(document);
	}

	public static void updateNoneStat(String workFlowID, String componentID, String typeName) {
		updateStat(workFlowID, componentID, typeName, STAT_NONE, 0, 0);
	}

	public static void updateRunningStat(String workFlowID, String componentID, String typeName) {
		updateStat(workFlowID, componentID, typeName, STAT_START, System.currentTimeMillis(), 0);
	}

	public static void updateSuccessStat(String workFlowID, String componentID, String typeName) {
		updateStat(workFlowID, componentID, typeName, STAT_SUCCESS, -1, System.currentTimeMillis());
	}

	public static void updateFailedStat(String workFlowID, String componentID, String typeName) {
		updateStat(workFlowID, componentID, typeName, STAT_FAILED, -1, System.currentTimeMillis());
	}

	private static void updateStat(String workFlowID, String componentID, String typeName, int status, long startTime,
			long endTime) {
		Document document = new Document("workFlowID", workFlowID).append("componentID", componentID)
				.append("typeName", typeName).append("status", status);
		if (startTime > -1) {
			document.append("startTime", startTime);
		}
		if (endTime > -1) {
			document.append("endTime", endTime);
		}
		Document modifiers = new Document();
		modifiers.append("$set", document);
		MongoCollection<Document> collection = mongoDatabase.getCollection(statusTable);
		UpdateOptions updateOptions = new UpdateOptions();
		updateOptions.upsert(true);
		
		collection.updateOne(Filters.and(Filters.eq("workFlowID", workFlowID), Filters.eq("componentID", componentID)),
				modifiers, updateOptions);
	}

	public static MongoCollection<Document> read() {
		MongoCollection<Document> collection = mongoDatabase.getCollection(statusTable);
		return collection;
	}
	public static void remove(String workFlowID) {
		MongoCollection<Document> collection = mongoDatabase.getCollection(statusTable);
		collection.deleteMany(Filters.eq("workFlowID", workFlowID));
	}
	

	public static void closeMongo() {
		mongoClient.close();
	}
}
