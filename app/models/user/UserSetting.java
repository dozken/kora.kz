package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class UserSetting extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	/**
	 * TODO 1. poluchat rassylku 2. uvedomlenie pm 3. uvedomlenie commentov
	 */

	// public User user;

	public String setting;

	public String status;
}
