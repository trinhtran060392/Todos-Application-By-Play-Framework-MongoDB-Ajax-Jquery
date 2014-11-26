package util;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * 
 * @author TrinhTV3
 *  Class MongoDBJDBC to connect database
 */
public class MongoDBJDBC {
  private int max;
  static MongoClient client;
  /**
   * 
   * @return a connection
   */
  public DB getDB() {
    try {
      if (client==null){
      client = new MongoClient("localhost", 27017);
      }
      // connect
      DB db = client.getDB("todoList");
      return db;
    } catch (Exception e)
    {
      return null;
    }
    finally{
   //  client.close();
    }

  }
  
  public void closeConnection(){
    client.close();
  }
  /**
   * 
   * @return max id of data in collection
   */
  public int getMaxID() {
    DBCollection todoTable = getDB().getCollection("TodoList");
    DBObject sort = new BasicDBObject();
    sort.put("id", -1);
    DBCursor cursor = todoTable.find().sort(sort).limit(1);
    while (cursor.hasNext()) {
      max = ((BasicBSONObject) cursor.next()).getInt("id");
    }
    
    return max;

  }
}
