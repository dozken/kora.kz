package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
public class Tag extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;
	
	@ManyToMany(mappedBy="tags")
	public List<Ad> ads;
}
