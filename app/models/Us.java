package models;

import javax.persistence.Entity;

import play.data.validation.Constraints;

@Entity
public class Us extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	 @Constraints.Required
	 public String name;
	 
	 public String description;
	 
	 public void init() {
		 this.id = System.currentTimeMillis();
	 }
}
