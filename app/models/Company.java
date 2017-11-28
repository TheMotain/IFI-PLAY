package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;



/**
 * Company entity managed by Ebean
 */
@Entity 
public class Company extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;
    
    @Constraints.Required
    public String name;

}

