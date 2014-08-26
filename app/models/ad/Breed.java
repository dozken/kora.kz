package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Breed extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Finder<Long, Breed> find = new Finder<Long, Breed>(
			Long.class, Breed.class);
	@Id
	public Long id;

	public String name;

	@OneToMany
	public Ad ad;

}
