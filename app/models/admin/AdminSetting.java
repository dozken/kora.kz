package models.admin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.Setting;
import models.user.AuthorisedUser;
import play.db.ebean.Model;

@Entity
@Table(name = "admin_settings")
public class AdminSetting extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, AdminSetting> find = new Model.Finder<Long, AdminSetting>(
			Long.class, AdminSetting.class);

	@Id
	public Long id;

	@ManyToOne
	public AuthorisedUser user;

	@ManyToOne
	public Setting setting;

	public String status;

}
