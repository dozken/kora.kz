package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="images")
public class Image extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	public Ad ad;
	
	@Id
	public Long id;
	
	public String image;
	
	
	
	//TODO
}
