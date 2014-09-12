package models.ad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import java.util.List;

@Entity
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

	public static String getFirstPicture(Long id){

        return AdImage.find.where().eq("ad_id",id).eq("position",1).findUnique().content;

    }

    public static List<AdImage> getPictureByPosition(Long id){

        return AdImage.find.where().eq("ad_id",id).order("position").findList();

    }

}
