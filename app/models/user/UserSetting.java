package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.Setting;
import play.db.ebean.Model;

@Entity
@Table(name="settings_user")
public class UserSetting extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, UserSetting> find = new Model.Finder<Long, UserSetting>(
			Long.class, UserSetting.class);

	@Id
	public Long id;

	@ManyToOne
	public AuthorisedUser user;

	@ManyToOne
	public Setting setting;

	public String status;
}
