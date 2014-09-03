package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import models.user.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class User extends Controller {

	public static Result register() {
		Form<AuthorisedUser> userForm = form(AuthorisedUser.class).bindFromRequest();
		if(userForm.hasErrors()){
			flash("error","Ошибка при заполнении формы!");
			return ok();
		}
		else if (AuthorisedUser.find.where().eq("email", userForm.get().email).findUnique() == null) {
			AuthorisedUser user = userForm.get();
			user.roles = new ArrayList<SecurityRole>();
			if(AuthorisedUser.findByEmail(session("connected")).roles.contains(SecurityRole.findByName("admin")))
				user.roles.add(SecurityRole.findByName("moderator"));
			else
				user.roles.add(SecurityRole.findByName("user"));
            Profile p = new Profile();
            user.profile=p;

            UserSocials socials = new UserSocials();
            socials.socialNetwork = SocialNetwork.find.byId(1L);
            socials.value="asik brat";

            user.userSocials.add(socials);

            user.save();


            session("connected", user.email);
			if(AuthorisedUser.findByEmail(session("connected")).roles.contains(SecurityRole.findByName("admin")))
				user.roles.add(SecurityRole.findByName("moderator"));
			else
				user.roles.add(SecurityRole.findByName("user"));
			user.save();
			return ok("success");
		} else {
			flash("error","Эта почта уже используется!, попробуйте другую.");
			return ok("error");
		}
		
	}
}
