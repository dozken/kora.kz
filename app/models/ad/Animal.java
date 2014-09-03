package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
public class Animal extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Model.Finder<Long, Animal> find = new Model.Finder<Long, Animal>(
			Long.class, Animal.class);

	@Id
	public Long id;
	
	public String name;
	
	@OneToMany
	public List<Ad> ads;
	
	@OneToMany
	public List<Breed> breeds;
	
	public List<Breed> getBreeds(){
		return Breed.find.where().eq("animal", this).orderBy("name").findList();
	}
}
