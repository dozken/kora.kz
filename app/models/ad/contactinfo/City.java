package models.contactinfo;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class City extends Model {

	@Id
	public Long id;
	
	public String name;
	
}
