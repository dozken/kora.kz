package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.ad.Image;
import models.contact.City;
import models.contact.Region;
import play.db.ebean.Model;

@Entity
public class Profile extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@OneToOne(mappedBy="profile")
	public AuthorisedUser user;

	@OneToOne
	public Image image;

	public String address;

	@ManyToOne
	public Region region;
	
	@ManyToOne
	public City city;

	public String gender;

	public String description;

	public String phone;

	// @ManyToMany
	// public SocialNetwork sn;

}
