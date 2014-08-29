package models.admin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.Setting;
import models.user.AuthorisedUser;
import play.db.ebean.Model;

@Entity
public class AdminSetting extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * on/off autoprodlenie
	 * on/off vydelit obayvlenie
	 * on/off pohojie
	 * on/off goryachie
	 * 
	 */
	
	@Id
	public Long id;
	
	@ManyToOne
	public AuthorisedUser user;
	
	@ManyToOne
	public Setting setting;

	public String status;
	
}
