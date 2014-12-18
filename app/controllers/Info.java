package controllers;

import static play.data.Form.form;

import java.io.File;

import models.Location;
import models.ad.Image;
import models.contact.City;
import models.contact.ContactInfo;
import models.contact.Region;
import models.user.AuthorisedUser;
import models.user.UserSocials;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.profile.info.edit.myInfoEdit;
import be.objectify.deadbolt.java.actions.SubjectPresent;

@SubjectPresent
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
			user.profile.city = City.find.byId(Long.parseLong(requestData
					.get("city")));
			user.userSocials.add(UserSocials.fillSocilal("vk",
					requestData.get("vk")));
			user.userSocials.add(UserSocials.fillSocilal("facebook",
					requestData.get("facebook")));
			user.userSocials.add(UserSocials.fillSocilal("website",
					requestData.get("website")));
			user.userSocials.add(UserSocials.fillSocilal("skype",
					requestData.get("skype")));

			user.profile.phone = ContactInfo.phoneCheck(user,
					requestData.get("phone"));

			if (user.profile.phone.equals("exists")) {
				return ok("phone_exists");
			}

			user.email = AuthorisedUser.checkMail(user,
					requestData.get("email"));

			if (user.email.equals("exists")) {
				return ok("mail_exists");
			}

			if (requestData.get("location") != null
					&& !requestData.get("location").equals("")) {
				if (user.location != null) {
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
			session("connected", user.email);
			return ok("success");// renderImage(icon.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok("baaaaa");
	}

}
