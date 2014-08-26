package models.contact;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class ContactInfo extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@ManyToOne
	public Region region;
	
	@ManyToOne
	public City city;
	
	public String company;
	
	public List<String> phone;
	
	public String email;
	
	public String pointOnMap;	
	
}
