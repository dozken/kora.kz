package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "locations")
public class Location extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Finder<Long, Location> find = new Finder<Long, Location>(
			Long.class, Location.class);

	@Id
	public Long id;

	public String lat;

	public String lng;

}
