package controllers;

import static play.data.Form.form;
import models.admin.AdminSetting;
import models.user.AuthorisedUser;
import models.user.UserSetting;
import play.api.libs.Crypto;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.settings._newsletterTab;
import views.html.profile.settings._passwordTab;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;

@SubjectPresent
public class Settings extends Controller {

	public static Result changePassword() {
		DynamicForm requestData = form().bindFromRequest();
		AuthorisedUser user = models.user.AuthorisedUser
				.findByEmail(session("connected"));
		String currentPassoword = requestData.get("current");
		String newPassoword = requestData.get("new");
		String repeatPassoword = requestData.get("repeat");

		currentPassoword =  Crypto.encryptAES(currentPassoword);
		if (!newPassoword.equals(repeatPassoword)) {
			flash("error",
					"Ошибка при подтверждения пароля, попробуйте еще раз.");
			return ok(_passwordTab.render());
		}

		if (user.password.equals(currentPassoword)) {
			flash("success", "Пароль успешно изменен!");
			user.password = Crypto.encryptAES(newPassoword);;
			user.update();
			return ok(_passwordTab.render());
		} else {
			flash("error", "Проверьте правильность текущего пароля.");
			return ok(_passwordTab.render());
		}
	}

	public static Result changeUserSetting(Long id, String status) {
		UserSetting userSetting = UserSetting.find.byId(id);
		userSetting.status = status;
		userSetting.update();
		return ok(_newsletterTab.render());
	}

	@Restrict(@Group("admin"))
	public static Result changeAdminSetting(Long id, String status) {
		AdminSetting adminSetting = AdminSetting.find.byId(id);
		adminSetting.status = status;
		adminSetting.update();
		return ok(_newsletterTab.render());
	}

	public static Result notification(Object object, Boolean state) {
		return TODO;
	}

	public static Result autoExtend() {
		return TODO;
	}
}
