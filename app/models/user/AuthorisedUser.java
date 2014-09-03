package models.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.admin.AdminSetting;
import play.db.ebean.Model;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

@Entity
public class AuthorisedUser extends Model implements Subject {

    public static final Model.Finder<Long, AuthorisedUser> find = new Model.Finder<Long, AuthorisedUser>(
            Long.class, AuthorisedUser.class);

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String userName;

	@Column(unique=true)
	public String email;

	public String password;
	
	@OneToOne(mappedBy="user",cascade=CascadeType.ALL)
	public Profile profile;

	@ManyToMany
	public List<SecurityRole> roles;

	@ManyToMany
	public List<UserPermission> permissions;

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	public List<UserSetting> userSettings;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	public List<AdminSetting> adminSettings;


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
	
	public static List<AuthorisedUser> getUsers(){
		return find.where().in("roles", SecurityRole.findByName("user")).findList();
	}
	
	public static List<AuthorisedUser> getModerators(){
		return find.where().in("roles", SecurityRole.findByName("moderator")).findList();
	}
	
	public static String md5(String password){
		return password;
	}
}
