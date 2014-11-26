package controllers;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import models.Task;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import util.MongoDBJDBC;

/**
 * 
 * @author TrinhTV3
 *  Class Application to receive request and handle
 */
public class Application extends Controller {

  public static MongoDBJDBC getConnect = new MongoDBJDBC();

  /**
   * show home screen with list task in database
   * @return
   */
  public static Result index() {
    return ok(views.html.index.render(Task.all()));
  }

  @BodyParser.Of(BodyParser.Json.class)
  public static Result tasks() {
    return ok();
  }
  
  /**
   * create new Task
   * @return json data
   *  
   */
  public static Result newTask() {
    int id = getConnect.getMaxID();
    System.out.println(id);
    Map<String, String[]> values = request().body().asFormUrlEncoded();
    String todoName = values.get("name")[0];
    Task task = new Task();
    task.setId(id + 1);
    task.setName(todoName);
    Task.create(task);
    
    return ok(Integer.toString(id + 1));  
  }
  
  /**
   * delete task
   * @return home screen
   */
  public static Result deleteTask() { 
    Map<String, String[]> values = request().body().asFormUrlEncoded();
    String id = values.get("id")[0];
    System.out.println(id);
    Task.delete(Integer.parseInt(id));
    
    return redirect(routes.Application.index()); 
  }
  
  /**
   * edit task
   * @return home screen
   */
  public static Result editTask(){ 
    Map<String, String[]> values = request().body().asFormUrlEncoded();
    String id = values.get("id")[0];
    String name = values.get("name")[0];
    Task.edit(Integer.parseInt(id),name);
    
    return redirect(routes.Application.index());  
  }
  
  /**
   * delete all task selected
   * @return
   */
  public static Result deleteAll() {
    Map<String, String[]> values = request().body().asFormUrlEncoded();
    String[] id_all = values.get("id_all[]"); 
    Task.deleteAll(id_all);
    
    return redirect(routes.Application.index());
  }
  
  
}
