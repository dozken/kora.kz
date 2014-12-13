package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "ad_tags")
public class Tag extends Model {

	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String name;

	@ManyToOne
	public Ad ad;

	public Tag(String tag) {

		name = tag;
	}
}
