package models.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

@Entity
@Table(name = "users")
public class AuthorisedUser extends Model implements Subject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String userName;

	public String email;

	public String password;

	@ManyToMany
	public List<SecurityRole> roles;

	@ManyToMany
	public List<UserPermission> permissions;

	public static final Finder<Long, AuthorisedUser> find = new Finder<Long, AuthorisedUser>(
			Long.class, AuthorisedUser.class);

	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String getIdentifier() {
		return email;
	}

	public static AuthorisedUser findByEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}
}
