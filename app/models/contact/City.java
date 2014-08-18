package models.contact;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.ad.Ad;
import play.db.ebean.Model;

@Entity
public class City extends Model {

	@Id
	public Long id;
	
	public String name;
	
	@OneToMany
	public ContactInfo contactInfo;
	
}
