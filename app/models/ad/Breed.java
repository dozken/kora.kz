package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

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
	
	@ManyToOne
	public Animal animal;

	public String name;

	@OneToMany
	public List<Ad> ads;

}
