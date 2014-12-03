package models.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.ad.Image;
import models.contact.City;
import models.contact.Region;
import models.payment.Qiwi;
import play.db.ebean.Model;

@Entity
public class Profile extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Profile> find = new Model.Finder<Long, Profile>(
			Long.class, Profile.class);
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
	
	@Column(unique=true,nullable=true)
	public String phone;

    public Double myMonney=0.0;
	// @ManyToMany
	// public SocialNetwork sn;

}
