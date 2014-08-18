package models.ad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Comment extends Model {
	
	@Id
	public Long id;
	
	@ManyToOne
	public Ad ad;
	
	public String name;
	
	public String email;
	
	public String text;
	
	public Date sendDate = new Date();
	
	

}
