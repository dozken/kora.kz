package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Breed extends Model {
	
	@Id
	public Long id;
	
	public String name;

}
