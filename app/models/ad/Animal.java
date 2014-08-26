package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.user.SecurityRole;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Animal extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Finder<Long, Animal> find = new Finder<Long, Animal>(
			Long.class, Animal.class);

	@Id
	public Long id;
	
	public String name;
	
	@OneToMany
	public Ad ad;
	
}
