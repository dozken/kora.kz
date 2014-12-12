package models.ad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "ad_settings")
public class AdSetting extends Model {

	/**
	 * 
	 */

	public static final Model.Finder<Long, AdSetting> find = new Model.Finder<Long, AdSetting>(
			Long.class, AdSetting.class);
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@ManyToOne
	public Ad ad;
	/**
	 * TODO 1. only registered can comment 2. auto extend expiration date of ad
	 * 3. allocate ad(vydelit')
	 */

	public String name;

	public String status;

	public Date validDate = new Date(new Date().getTime()
			+ (1000 * 60 * 60 * 24 * 7));
}
