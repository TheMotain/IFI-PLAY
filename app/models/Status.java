package models;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente les status possible d'une tâche
 *
 */
public enum Status{
	  TODO("TODO", 1),
	  IN_PROGRESS("IN PROGRESS", 2),
	  READY_FOR_TEST("READY FOR TEST", 3),
	  DONE("DONE", 4);
	   
	  private String name;
	  private long id;
	   
	  Status(String name, long id){
	    this.name = name;
	    this.id = id;
	  }
	   
	  public String getName(){
		  return name;
	  }
	  
	  public long getId() {
		  return id;
	  }
	  
		 
	 public Map<String, String> getMap() {
		 Map<String, String> map = new HashMap<>(); 
		 map.put(String.valueOf(TODO.getId()), TODO.getName());
		 map.put(String.valueOf(IN_PROGRESS.getId()), IN_PROGRESS.getName());
		 map.put(String.valueOf(READY_FOR_TEST.getId()), READY_FOR_TEST.getName());
		 map.put(String.valueOf(DONE.getId()), DONE.getName());
		 return map;
	 }
}
