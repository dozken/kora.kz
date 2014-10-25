package models.ad;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

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
	

	public String quantity;
	
	public Integer birthDate;
	
	@ManyToOne(cascade= CascadeType.ALL)
	public Price priceType;
	
	public String title;
	
	@Column(columnDefinition = "TEXT")
	public String description;
	
	@OneToMany(cascade= CascadeType.ALL)
	public List<AdImage> images;
	
	@OneToOne(cascade= CascadeType.ALL)
	public ContactInfo contactInfo;

    @OneToMany(cascade= CascadeType.ALL)
	public List<Tag> tags = new ArrayList<Tag>();
    
    @OneToMany(cascade= CascadeType.ALL)
   	public List<AdSetting> settings = new ArrayList<AdSetting>();
	
	public String status = "pending";
	
	public Integer views = 0;
	
	public Integer shares = 0;
	
//	TODO
//	public AdSetting adSettings;
	
	public Date publishedDate = new Date();
	
	@Version
	public Date updatedDate = new Date();	
	
	public Date expirationDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7));
	
	@OneToMany
	public List<PrivateMessage> messages = new ArrayList<PrivateMessage>();
	
	@OneToMany
	public List<Comment> comments  = new ArrayList<Comment>();

    public List<AdImage> sortByPosition(Long id){

        return AdImage.find.where().eq("ad_id",id).order("position").findList();
    }

    public static AdSetting settingByName(Ad ad,String name){
    	
    	return AdSetting.find.where().eq("name" , name).eq("ad", ad).findUnique();
    }
    
    public static String dateDifference(Date d)
    {
    	
//    	LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//    	LocalDate today = LocalDate.now();
//
//
//    	Period p = Period.between(today, date);
//    	return p.getDays() + " дней";
    	return "needto implemented";
    }
}

