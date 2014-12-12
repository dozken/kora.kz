package models.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

@Entity
@Table(name = "user_socials")
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

	public static UserSocials fillSocilal(String name, String value) {
		UserSocials socials = new UserSocials();
		socials.socialNetwork = SocialNetwork.find.where().eq("name", name)
				.findUnique();
		socials.value = value;
		return socials;

	}

	public static String getSocialByName(Long id, String name) {
		String sql = "select value from user_socials where user_id="
				+ id.toString()
				+ " and social_network_id in (select id from socials where name='"
				+ name + "')";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> list = sqlQuery.findList();
		if (list.size() > 0) {
			return myTrim(list.get(0).values().toString());
		}
		return "";
	}

	public static String myTrim(String a) {
		String b = "";
		for (int i = 1; i < a.length() - 1; i++) {
			b += a.charAt(i);

		}
		return b;
	}

}
