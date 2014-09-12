package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

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

	public byte[] content;

	@Constraints.Required
	public String contentType;

    public Integer position;

	

}
