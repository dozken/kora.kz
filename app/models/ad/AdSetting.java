package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="settings_ad")
public class AdSetting extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
