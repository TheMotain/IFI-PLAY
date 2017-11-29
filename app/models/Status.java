package models;

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
	  
}
