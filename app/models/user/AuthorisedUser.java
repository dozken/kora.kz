package models.user;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table(name = "users")
public class AuthorisedUser extends Model implements Subject {

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
