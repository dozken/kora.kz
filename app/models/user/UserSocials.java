package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="socials_user")
public class UserSocials extends Model {

    private static final long serialVersionUID = 1L;

    public static final Model.Finder<Long, UserSocials> find = new Model.Finder<Long, UserSocials>(
            Long.class, UserSocials.class);

    @Id
    public Long id;

	@ManyToOne
	public AuthorisedUser user;

   @ManyToOne
   public SocialNetwork socialNetwork;

    public String value;

}


