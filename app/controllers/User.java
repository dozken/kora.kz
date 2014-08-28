package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import models.user.AuthorisedUser;
import models.user.SecurityRole;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class User extends Controller {

	public static Result register() {
		Form<AuthorisedUser> userForm = form(AuthorisedUser.class).bindFromRequest();
		if(userForm.hasErrors()){
			return ok();
		}
		else if (AuthorisedUser.find.where().eq("email", userForm.get().email).findUnique() == null) {
			AuthorisedUser user = userForm.get();
			user.roles = new ArrayList<SecurityRole>();
			user.roles.add(SecurityRole.findByName("user"));
			user.save();
			return ok("success");
		} else {
			return ok("error");
		}
		// return redirect(routes.Manage.myInfo());
	}
}
