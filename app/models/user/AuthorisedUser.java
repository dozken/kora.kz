package models.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.Location;
import models.ad.Ad;
import models.ad.PrivateMessage;
import models.admin.AdminSetting;
import play.db.ebean.Model;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

@Entity
@Table(name = "users")
public class AuthorisedUser extends Model implements Subject {

	public static final Model.Finder<Long, AuthorisedUser> find = new Model.Finder<Long, AuthorisedUser>(
			Long.class, AuthorisedUser.class);

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String userName;

	@Column(unique = true)
	public String email;

	public String password;

	public String status = "waiting";

	@OneToOne(cascade = CascadeType.ALL)
	public Profile profile;

	@ManyToMany(cascade = CascadeType.ALL)
	public List<SecurityRole> roles;

	@ManyToMany
	public List<UserPermission> permissions;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public List<UserSetting> userSettings;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public List<UserSocials> userSocials;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public List<AdminSetting> adminSettings;

	@OneToOne(cascade = CascadeType.ALL)
	public Location location;

	@ManyToMany
	public List<Ad> favorites;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public List<Payment> payments = new java.util.ArrayList<Payment>();

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

	public static String checkMail(AuthorisedUser user, String mail) {

		if (user.email.equals(mail)) {
			return mail;
		} else {
			int row = AuthorisedUser.find.where().eq("email", mail)
					.findRowCount();

			if (row > 0) {
				return "exists";
			}
		}
		return mail;
	}

	public static List<AuthorisedUser> getUsers(int page, String sort) {
		List<AuthorisedUser> users = find.where()
				.in("roles", SecurityRole.findByName("user")).ne("id", 1L)
				.order("userName " + sort).findPagingList(20).getPage(page)
				.getList();

		// if(users!=null)
		// for(AuthorisedUser user : users){
		// if(user.roles.size()>1)users.remove(user);
		// }
		return users;
	}

	public static int getUsersCount() {

		return find.where().in("roles", SecurityRole.findByName("user"))
				.ne("id", 1L).findRowCount();
	}

	public static List<AuthorisedUser> getModerators(int page, String sort) {
		return find.where().in("roles", SecurityRole.findByName("moderator"))
				.order("userName " + sort).findPagingList(20).getPage(page)
				.getList();
	}

	public static int getModeratorsCount() {

		return find.where().in("roles", SecurityRole.findByName("moderator"))
				.findRowCount();
	}

	public static Integer getUnreadMessageCount(Long id) {

		return PrivateMessage.find.where().eq("recipent_id", id)
				.eq("status", "unread").findRowCount();
	}

	public static String md5(String password) {
		return password;
	}

	public static Double getMyMoney(String email) {
		return AuthorisedUser.findByEmail(email).profile.myMonney;
	}

}
