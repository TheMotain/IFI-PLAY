package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
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
	 
	 public Long status;
	 
	 public Task(Long id, String name, String description) {
		 this.id = id;
		 this.name = name;
		 this.description = description;
		 this.status = 1L;
	 }
	 
	 @PrePersist
	 void preInsert() {
	    if (this.status == null)
	        this.status = 1L;
	 }

}
