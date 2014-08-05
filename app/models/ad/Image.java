package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Image extends Model {

	@Id	
	public String image;
	
	//TODO
}
