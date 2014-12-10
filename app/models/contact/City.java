package models.contact;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.Location;
import models.user.Profile;
import play.db.ebean.Model;

@Entity
public class City extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, City> find = new Model.Finder<Long, City>(
			Long.class, City.class);
	@Id
	public Long id;

	@ManyToOne
	public Region region;

	@ManyToOne
	public City parentCity;

	@OneToMany(mappedBy = "parentCity")
	public List<City> subCities;

	public String name;

	@OneToOne(cascade = CascadeType.ALL)
	public Location location = new Location();

	@OneToMany(mappedBy = "city")
	public List<Profile> profiles;

	@OneToMany(mappedBy = "city")
	public List<ContactInfo> contactInfos;

	public List<City> findSubCitites() {
		return find.where().eq("parentCity", this).orderBy("name").findList();
	}

	public boolean isParent() {
		return (this.subCities != null && this.subCities.size() > 0) ? true
				: false;
	}

}
