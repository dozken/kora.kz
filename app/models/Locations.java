package models;

import models.admin.AdminSetting;
import models.user.UserSetting;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="locations")
public class Locations extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Finder<Long, Locations> find = new Finder<Long, Locations>(
			Long.class, Locations.class);
	
	@Id
	public Long id;
	
	public String lat;

    public String lng;



}
