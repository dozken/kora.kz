package models.ad;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.ad.contactinfo.ContactInfo;
import play.db.ebean.Model;

@Entity
public class Ad extends Model{

	@Id
	public Long id;
	
	@ManyToOne
	public Animal animalType;
	
	@ManyToOne
	public Breed breedType;
	
	public String gender;
	
	@ManyToOne
	public Quantity quantity;
	
	public Date birthDate;
	
	@ManyToOne
	public Price priceType;
	
	public String title;
	
	public String description;
	
	@OneToMany
	public List<Image> images;
	
	@ManyToOne
	public ContactInfo contactInfo;
	
	public String status;
	
	public Integer views;
	
	public Integer shares;
	
//	TODO
//	public AdSetting adSettings;
	
	public Date publishedDate = new Date();
	
	public Date updatedDate = new Date();	
	
	public Date expirationDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7));
	
	@OneToMany
	public List<PrivateMessage> messages;
	
	@OneToMany
	public List<Comment> comments;
}
