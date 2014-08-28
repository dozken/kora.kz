package models.contact;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

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
