package models.contact;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.user.Profile;
import play.db.ebean.Model;

@Entity
public class Region extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Region> find = new Model.Finder<Long, Region>(
			Long.class, Region.class);
	
	@Id
	public Long id;
	
	public String name;
	
	@OneToMany(mappedBy="region")
	public List<Profile> profiles;
	
	@OneToMany(mappedBy="region")	
	public List<ContactInfo> contactInfos;
	
	@OneToMany(mappedBy="region")	
	public List<City> city;
	
}
