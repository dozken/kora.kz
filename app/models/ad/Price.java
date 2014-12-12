package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "ad_prices")
public class Price extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Price> find = new Model.Finder<Long, Price>(
			Long.class, Price.class);

	@Id
	public Long id;

	public Integer price = 0;

	public String currency = "";

	@OneToMany
	public List<Ad> ads;

	// public Double price;

}
