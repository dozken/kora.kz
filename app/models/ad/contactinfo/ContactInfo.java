package models.contactinfo;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

public class ContactInfo extends Model {

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
