package models.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

import java.util.List;

@Entity
public class SocialNetwork extends Model {

    public static final Model.Finder<Long, SocialNetwork> find = new Model.Finder<Long, SocialNetwork>(
            Long.class, SocialNetwork.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;

}
