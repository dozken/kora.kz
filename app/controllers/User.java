package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import models.Emailing;
import models.user.AuthorisedUser;
import models.user.Profile;
import models.user.SecurityRole;
import play.api.libs.Crypto;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.forgotPassword;
import views.html.common.registerConfirm;
import views.html.common.restorePassword;
import views.html.mailBody.recoverPassword;

import com.avaje.ebean.Ebean;

public class User extends Controller {

	public static Result register() {
		Form<AuthorisedUser> userForm = form(AuthorisedUser.class)
				.bindFromRequest();
		if (userForm.hasErrors()) {
			flash("error", "Ошибка при заполнении формы!");
			return ok();
		} else if (AuthorisedUser.find.where()
				.eq("email", userForm.get().email).findUnique() == null) {

			AuthorisedUser user = userForm.get();
			user.password = Crypto.encryptAES(userForm.get().password);

			user.roles = new ArrayList<SecurityRole>();
			user.profile = new Profile();

			if (session("connected") != null
					&& AuthorisedUser.findByEmail(session("connected")).roles
							.contains(SecurityRole.findByName("admin"))) {
				user.roles.add(SecurityRole.findByName("moderator"));
			} else {
				user.roles.add(SecurityRole.findByName("user"));
				// session("connected", user.email);
			}
			user.save();
			Ebean.saveManyToManyAssociations(user, "roles");

			return ok(user.email);
		} else {
			flash("error", "Эта почта уже используется!, попробуйте другую.");
			return ok("error");
		}

	}

	public static Result confirmPage() {
		return ok(registerConfirm.render());
	}

	public static Result forgotPassword() {
		return ok(forgotPassword.render());
	}

	public static Result resetPassword() {
		DynamicForm requestData = form().bindFromRequest();
		String email = requestData.get("email");

		AuthorisedUser user = AuthorisedUser.findByEmail(email);
		if (user != null) {
			flash("success",
					"Инструкции по смене пароля отправлены на адрес <b>"
							+ email + "</b>");
			Emailing.send("Восстановление пароля от Қора.kz",
					new String[] { user.userName + " <" + user.email + ">" },
					recoverPassword.render(user).body());
		} else {
			flash("error",
					"Личный кабинет с указанным логином не зарегистрирован, хотите <a href='#' data-toggle='modal' data-target='#myModal2'>зарегистрироваться</a>?");
		}
		return redirect(request().getHeader("referer"));
	}

	public static Result restorePassword(String code) {
		return ok(restorePassword.render(code));
	}

	public static Result confirmRegistration(String code) {

		AuthorisedUser user = AuthorisedUser.findByEmail(play.libs.Crypto
				.decryptAES(code));

		user.status = "active";
		user.update();
		session("connected", user.email);
		return redirect(routes.Manage.myInfo());
	}

	public static Result changePassword(String code) {
		AuthorisedUser user = AuthorisedUser.findByEmail(play.libs.Crypto
				.decryptAES(code));
		DynamicForm requestData = form().bindFromRequest();
		String newPassoword = requestData.get("newpassword");
		String repeatPassoword = requestData.get("repeatpassword");
		if (!newPassoword.equals(repeatPassoword)) {
			flash("error",
					"Ошибка при подтверждения пароля, попробуйте еще раз.");
			return ok(restorePassword.render(code));
		} else {
			user.password = Crypto.encryptAES(newPassoword);
			user.update();
			flash("success",
					"Пароль успешно изменен! Хотите <a href='#' data-toggle='modal' data-target='#myModal'>войти</a>? ");
		}
		return redirect(request().getHeader("referer"));
	}

	public static Result removeUser(Long id, Integer page, String sort) {
		AuthorisedUser.find.byId(id).delete();
		return ok(views.html.admin.users.adminUsers.render(
				AuthorisedUser.getUsers(page, sort), page, sort,AuthorisedUser.getUsersCount()));
	}

	public static Result removeModerator(Long id,Integer page, String sort) {
		AuthorisedUser.find.byId(id).delete();
		return ok(views.html.admin.moderators.adminModerators.render(AuthorisedUser.getModerators(page, sort), page, sort,AuthorisedUser.getModeratorsCount()));
	}

}
