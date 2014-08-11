package models;

import javax.persistence.Entity;
import javax.persistence.Id;


import play.db.ebean.Model;

@Entity
public class UserSettings extends Model {

	@Id
	public Long id;	
	
	/**
	 * TODO
	 * 1. poluchat rassylku
	 * 2. uvedomlenie pm
	 * 3. uvedomlenie commentov
	 */
	
//	public User user;
	
	public String setting;
	
	public Boolean status;
}
