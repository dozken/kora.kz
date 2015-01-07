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

	public static String getFirstPicture(Long id) {

		AdImage adImage = AdImage.find.where().eq("ad_id", id).eq("position", 1).findUnique();
		return adImage.content;

	}

	public static List<AdImage> getPictureByPosition(Long id) {

		return AdImage.find.where().eq("ad_id", id).order("position")
				.findList();

	}

}
