package models.contact;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Region extends Model {

	@Id
	public Long id;
	
	public String name;
}
