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
import javax.persistence.Table;
import javax.persistence.Version;

import models.contact.ContactInfo;
import play.db.ebean.Model;
import play.mvc.Controller;

@Entity
@Table(name = "ads")
public class Ad extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Ad> find = new Model.Finder<Long, Ad>(
			Long.class, Ad.class);

	@Id
	public Long id;

	@ManyToOne
	public Section section;

	@ManyToOne
	public Category category;

	public String gender = "Другой";

	public String quantity;

	public Integer birthDate;

	@ManyToOne(cascade = CascadeType.ALL)
	public Price priceType;

	public String title;

	@Column(columnDefinition = "TEXT")
	public String description;

	@OneToMany(cascade = CascadeType.ALL)
	public List<AdImage> images;

	@OneToOne(cascade = CascadeType.ALL)
	public ContactInfo contactInfo;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Tag> tags;

	@OneToMany(cascade = CascadeType.ALL)
	public List<AdSetting> settings;

	public String status = "pending";

	public Integer views = 0;

	public Integer shares = 0;

	// TODO
	// public AdSetting adSettings;

	public Date publishedDate = new Date();

	@Version
	public Date updatedDate = new Date();

	public Date expirationDate = new Date(new Date().getTime() + 1000 * 60 * 60
			* 24 * 7);

	@OneToMany(cascade = CascadeType.ALL)
	public List<PrivateMessage> messages = new ArrayList<PrivateMessage>();

	@OneToMany(cascade = CascadeType.ALL)
	public List<Comment> comments = new ArrayList<Comment>();

	public List<AdImage> sortByPosition(Long id) {

		return AdImage.find.where().eq("ad_id", id).order("position")
				.findList();
	}

	public static AdSetting settingByName(Ad ad, String name) {

		return AdSetting.find.where().eq("name", name).eq("ad", ad)
				.findUnique();
	}

	public static String dateDifference(Date d) {

		Date today = new Date();
		long res = d.getTime() - today.getTime();
		Long days = res / (24 * 60 * 60 * 1000) + 1;
		if (days == 1) {
			return days.toString() + " день";
		}
		if (days <= 0) {
			return "update expirity day or archive it method";
		}
		return days.toString() + " дней";
	}

	public static List<Ad> like(Ad ad) {
		return find.where().eq("category", ad.category).ne("id", ad.id)
				.eq("status", "active")
				.eq("contactInfo.city", ad.contactInfo.city).setMaxRows(7)
				.findList();
	}

	public static List<Ad> findToModerate() {
		return find
				.where()
				.or(com.avaje.ebean.Expr.eq("status", "pending"),
						com.avaje.ebean.Expr.eq("status", "moderating"))
				.findList();

	}

	public static AdImage getfirstPic(Long id){
		return AdImage.find.where().eq("ad_id",id).eq("possition",1).findUnique();
	}

	public static void sessionRegion(Long id) {
		Controller.session("region", id.toString());
	}
}
