package models.admin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import play.db.ebean.Model;

@Entity
public class Advertisement extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Advertisement> find = new Model.Finder<Long, Advertisement>(
			Long.class, Advertisement.class);
	
	@Id
	public Long id;
	
	public Integer position;// = find.where().;
	
	public String company;
	
	public String placeOnPage;
	
	public String embeddedObject;
	
	public Date published = new Date();
	
	@Lob
	public byte[] file;

	public String fileType;
	
	public String status;
}
