package cn.com.carenet.scheduler.utils;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/7/11
  */
public class MongoDBUtils {
	public static List<Document> findByWorkFlowId(MongoDatabase database, String table, String workFlowId) {
    	
    	List<Document> list = new ArrayList<>();
    	MongoClient client = null;
        try {
            MongoCollection<Document> users = database.getCollection(table);
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("workFlowID", workFlowId);
            FindIterable<Document> find = users.find(searchQuery);
            MongoCursor<Document> iterator = find.iterator();
            while(iterator.hasNext()){
            	Document document = iterator.tryNext();
            	list.add(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	if(client != null) client.close();
		}
        return list;
        
    }
}
