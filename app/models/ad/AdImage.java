package models.ad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.Play;
import play.db.ebean.Model;

@Entity
@Table(name = "ad_images")
public class AdImage extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Finder<Long, AdImage> find = new Finder<Long, AdImage>(
			Long.class, AdImage.class);

	@ManyToOne
	public Ad ad;

	@Id
	public Long id;

	public String name;

	@Column(columnDefinition = "TEXT")
	public String content;

	public Integer position;

	@Column(columnDefinition = "TEXT")
	public String additional;

	@Column(columnDefinition = "TEXT")
	public String small;

	public Integer h;

	public Integer w;

	public static String getFirstPicture(Long id) {

		AdImage adImage = AdImage.find.where().eq("ad_id", id).eq("position", 1).findUnique();
		return adImage.content;

	}

	public static String getFirstPictureA(Long id) {

		AdImage adImage = AdImage.find.where().eq("ad_id", id).eq("position", 1).findUnique();
		return adImage.additional;

	}
	public static String getFirstPictureSmall(Long id) {

		AdImage adImage = AdImage.find.where().eq("ad_id", id).eq("position", 1).findUnique();
		return adImage.small;

	}

	public static String getFirstPictureS(Long id) {

		AdImage img = AdImage.find.where().eq("ad_id", id).eq("position", 1).findUnique();

		double a = ((230.0 - ((img.w) * (157.0 / img.h))) / 2);
		if(a>0)
		return "margin-left:" + a + "px;";
		return "";
	}

	public static List<AdImage> getPictureByPosition(Long id) {

		return AdImage.find.where().eq("ad_id", id).order("position")
				.findList();

	}

}
