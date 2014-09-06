package controllers;

import static play.data.Form.form;

import java.io.File;
import java.util.List;

import models.Location;
import models.ad.Image;
import models.contact.Region;
import models.user.AuthorisedUser;
import models.user.SocialNetwork;
import models.user.UserSocials;

import org.apache.commons.codec.binary.Base64;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.profile.info.edit.myInfoEdit;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

public class Info extends Controller {

	public static Result edit() {

		return ok(myInfoEdit.render(AuthorisedUser
				.findByEmail(session("connected"))));
	}

	public static Result update(Long id) {

		play.mvc.Http.MultipartFormData body = request().body()
				.asMultipartFormData();

		DynamicForm requestData = form().bindFromRequest();
		AuthorisedUser user = AuthorisedUser.find.byId(id);
		try {
			for (int i = 0; i < body.getFiles().size(); i++) {
				Http.MultipartFormData.FilePart picture = body.getFiles()
						.get(i);

				if (picture != null) {
					File file = picture.getFile();
					Image icon = new Image();
					byte[] ima = com.google.common.io.Files.toByteArray(file);
					icon.contentType = picture.getContentType();
					icon.content = ima;
					icon.save();
					user.profile.image = icon;
				}
			}
			user.profile.region = Region.find.byId(Long.parseLong(requestData
					.get("region")));
			user.userSocials.add(fillSocilal("vk", requestData.get("vk")));
			user.userSocials.add(fillSocilal("facebook",
					requestData.get("facebook")));
			user.userSocials.add(fillSocilal("website",
					requestData.get("website")));
			user.userSocials
					.add(fillSocilal("skype", requestData.get("skype")));
			user.profile.phone = requestData.get("phone");
			if (requestData.get("location") != null
					&& !requestData.get("location").equals("")) {
				System.out.println("1");
				if (user.location != null) {
					System.out.println("2");
					String[] loc = requestData
							.get("location")
							.substring(1,
									requestData.get("location").length() - 1)
							.split(",");
					if (loc.length == 2) {
						user.location.lat = loc[0].trim();
						user.location.lng = loc[1].trim();
					}
				} else {
					String[] loc = requestData
							.get("location")
							.substring(1,
									requestData.get("location").length() - 1)
							.split(",");
					if (loc.length == 2) {
						Location location = new Location();
						location.lat = loc[0].trim();
						location.lng = loc[1].trim();
						user.location = location;
					}
				}
			}
			user.profile.address = requestData.get("address");
			user.profile.description = requestData.get("description");
			user.profile.gender = requestData.get("gender");
			user.userName = requestData.get("company_name");
			user.update();
			return ok("success");// renderImage(icon.id);
		} catch (Exception e) {
			System.out.println(e);
		}
		return ok("baaaaa");
	}

	public static UserSocials fillSocilal(String name, String value) {
		UserSocials socials = new UserSocials();
		socials.socialNetwork = SocialNetwork.find.where().eq("name", name)
				.findUnique();
		socials.value = value;
		return socials;

	}

	public static String getSocialByName(Long id, String name) {
		String sql = "select value from socials_user where user_id="
				+ id.toString()
				+ " and social_network_id in (select id from social_network where name='"
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

	@SuppressWarnings("static-access")
	public static String byteToBase64(byte[] data) {
		Base64 base64 = new Base64();
		return base64.encodeBase64String(data);
	}
}
