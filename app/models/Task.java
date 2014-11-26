package models;

import java.util.ArrayList;
import java.util.List;

import util.MongoDBJDBC;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author TrinhTV3
 *  Class Task to manipulate with database, define object todo
 */
public class Task {

  public static List<Task> listTodo;
  public static MongoDBJDBC getConnect;
  public int id;
  public String name;

  /**
   * 
   * @return all document from collection TodoList
   */
  public static List<Task> all() {
    listTodo = new ArrayList<Task>();
    
    getConnect = new MongoDBJDBC();
    DBCollection todoCollection = getConnect.getDB().getCollection("TodoList");
    DBCursor cursor = todoCollection.find();
    
    Task task;
    while (cursor.hasNext()) {
      task = new Task();
      BasicDBObject obj = (BasicDBObject) cursor.next();
      
      task.setId(obj.getInt("id"));
      task.setName(obj.getString("name"));
      
      listTodo.add(task);
    }
    getConnect.closeConnection();
    return listTodo;
  }

  public static int size() {
    List<Task> listTodoList = Task.all();
    return listTodoList.size();
  }
  
  /**
   * 
   * @param task to insert into database
   */
  public static void create(Task task) {
    getConnect = new MongoDBJDBC();
    DBCollection todoCollection = getConnect.getDB().getCollection("TodoList");
    
    BasicDBObject document = new BasicDBObject();
   
    document.put("id", task.getId());
    document.put("name", task.getName());
    
    todoCollection.insert(document);
    
    getConnect.closeConnection();
  }
  
  /**
   * 
   * @param id to delete todo into database with id
   */
  public static void delete(Integer id) {
    getConnect = new MongoDBJDBC();
    DBCollection todoCollection = getConnect.getDB().getCollection("TodoList");
    
    DBObject query = new BasicDBObject();
    ((BasicDBObject) query).append("id", id);
    
    todoCollection.remove(query);
    
    getConnect.closeConnection();
  }

  /**
   * 
   * @param id is to search document
   * @param name is value to update
   */
  public static void edit(Integer id, String name) {
    getConnect = new MongoDBJDBC();
    DBCollection todoCollection = getConnect.getDB().getCollection("TodoList");
    
    BasicDBObject searchUpdate = new BasicDBObject();
    searchUpdate.append("id", id);
    BasicDBObject objectUpdate = new BasicDBObject();
    objectUpdate.append("$set", new BasicDBObject("name", name));
    
    todoCollection.update(searchUpdate, objectUpdate);
    
    getConnect.closeConnection();
  }

   /**
    * 
    * @param id is array that store list id that user choose to delete
    */
  public static void deleteAll(String[] id) {
    getConnect = new MongoDBJDBC();
    DBCollection todoCollection = getConnect.getDB().getCollection("TodoList");
    
    int size = id.length;
    
    BasicDBObject object;
    for (int i = 0; i < size; i++) {
      object = new BasicDBObject();
      object.append("id", Integer.parseInt(id[i]));
      
      todoCollection.remove(object);
    }
    
    getConnect.closeConnection();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
