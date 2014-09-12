package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Image extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Finder<Long, Image> find = new Finder<Long, Image>(
			Long.class, Image.class);

	
	@Id
	public Long id;
	

	public byte[] content;

	@Constraints.Required
	public String contentType;
	
	
	//TODO
}
