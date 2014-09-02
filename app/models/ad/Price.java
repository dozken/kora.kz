package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	public String price = "";
	
	public String currency = "";
	
	@OneToMany
	public List<Ad> ads;
	
//	public Double price;
	
}
