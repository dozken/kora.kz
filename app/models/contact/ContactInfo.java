package models.contact;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	public List<String> phone;
	
	public String email;
	
	public String pointOnMap;	
	
	
	public ContactInfo (){
		
	}
	
	public ContactInfo (Region r, String c,String p, String e, String m){
		region = r;
		company = c;
		phone = new ArrayList<String>();
		phone.add(p);
		email = e;
		pointOnMap = m;
		save();
		
	}
}
