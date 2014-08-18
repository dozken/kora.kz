package models.ad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.user.AuthorisedUser;
import play.db.ebean.Model;

@Entity
public class PrivateMessage extends Model {

	@Id
	public Long id;
	
	@OneToOne
	public AuthorisedUser author;
	
	@OneToOne
	public AuthorisedUser recipent;
	
	@ManyToOne
	public Ad ad;
	
	public String title;
	
	public String message;
	
	public Date sendDate = new Date();
}
