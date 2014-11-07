package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Price extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Price> find = new Model.Finder<Long, Price>(
			Long.class, Price.class);
	
	@Id
	public Long id;
	
	public Double price = 0.0;
	
	public String currency = "";
	
	@OneToMany
	public List<Ad> ads;
	
//	public Double price;
	
}
