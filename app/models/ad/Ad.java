package models.ad;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.contact.ContactInfo;
import play.db.ebean.Model;

@Entity
public class Ad extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Ad> find = new Model.Finder<Long, Ad>(
			Long.class, Ad.class);

	@Id
	public Long id;
	
	@ManyToOne
	public Animal animal;
	
	@ManyToOne
	public Breed breed;
	
	public String gender = "Другой";
	
	@ManyToOne
	public Quantity quantity;
	
	public Date birthDate = new Date();;
	
	@ManyToOne
	public Price priceType;
	
	public String title;
	
	@Column(columnDefinition = "TEXT")
	public String description;
	
	@OneToMany
	public List<Image> images;
	
	@ManyToOne
	public ContactInfo contactInfo;
	
	@ManyToMany
	public List<Tag> tags;
	
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
