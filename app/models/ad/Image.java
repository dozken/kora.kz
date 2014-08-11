package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Image extends Model {

	@ManyToOne
	public Ad ad;
	
	@Id
	public Long id;
	
	public String image;
	
	
	
	//TODO
}
