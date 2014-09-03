package models.ad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
public class Comment extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@ManyToOne
	public Ad ad;
	
	public String name;
	
	public String email;
	
	@Column(columnDefinition = "TEXT")
	public String text;
	
	public Date sendDate = new Date();
	
	

}
