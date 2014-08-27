package models.contact;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.ad.Animal;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Region extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Finder<Long, Region> find = new Finder<Long, Region>(
			Long.class, Region.class);
	
	@Id
	public Long id;
	
	public String name;
	
	@OneToMany(mappedBy="region")	
	public List<ContactInfo> contactInfos;
	
}
