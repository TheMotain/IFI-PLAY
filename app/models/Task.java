package models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints;

/**
 *Objet représentant la tâche
 */
@Entity
public class Task extends BaseModel{
	
	 @Constraints.Required
	 public String name;
	 
	 public String description;
	 
	 @NotNull
	 public Long status;
	 
	 public Task(Long id, String name, String description) {
		 this.id = id;
		 this.name = name;
		 this.description = description;
		 this.status = 1L;
	 }
	 
	 public void init() {
		 this.id = System.currentTimeMillis();
	 }
}
