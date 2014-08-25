package models.ad;

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

	@Id
	public Long id;
	
	public String price;
	
	public String currency;
	
	@OneToMany
	public Ad ad;
	
//	public Double price;
	
}
