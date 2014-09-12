package models.contact;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.Location;
import play.db.ebean.Model;

@Entity
@Table(name="contact")
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
	
	public String phone;
	
	public String email;

    @OneToOne(cascade=CascadeType.ALL)
    public Location location;
	
	
	public ContactInfo (){
		
	}
	
	public ContactInfo (Region r, String c,String p, String e, String m){
		region = r;
		company = c;
		phone = "";

		email = e;

		save();
		
	}
}
