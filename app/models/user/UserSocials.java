package models.user;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import controllers.User;
import models.admin.AdminSetting;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

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


