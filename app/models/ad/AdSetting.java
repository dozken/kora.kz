package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import play.db.ebean.Model;

@Entity
public class AdSetting extends Model {

	@Id
	public Long id;
	
	@ManyToOne
	public Ad ad;
	/**
	 * TODO
	 * 1. only registered can comment
	 * 2. auto extend expiration date of ad
	 * 3. allocate ad(vydelit')
	 */
	
	public String name;
	
	public String status;
}
