package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.Setting;
import play.db.ebean.Model;

@Entity
public class UserSetting extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@ManyToOne
	public AuthorisedUser user;
	
	@ManyToOne
	public Setting setting;

	public String status;
}
