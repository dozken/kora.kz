package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Quantity extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Finder<Long, Quantity> find = new Finder<Long, Quantity>(
			Long.class, Quantity.class);
	
	@Id
	public Long id;
	
	public String name;
	
	@OneToMany
	public List<Ad> ads;
	
}
