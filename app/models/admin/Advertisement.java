package models.admin;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Advertisement extends Model{

	@Id
	public Long id;
	
	public String placeOnPage;
	
	public String embeddedObject;
	
	public Boolean show = true;
}
