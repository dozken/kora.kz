package models.admin;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	
//	public User user;
	
	public String setting;
	
	public Boolean status;
}
