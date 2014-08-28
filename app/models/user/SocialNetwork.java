package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class SocialNetwork extends Model {

	@Id
	public Long id;
	
	public String name;
}
