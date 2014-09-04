package controllers;

import static play.data.Form.form;
import models.admin.AdminSetting;
import models.user.AuthorisedUser;
import models.user.UserSetting;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.settings._newsletterTab;
import views.html.profile.settings._passwordTab;
public class Settings extends Controller {

	public static Result changePassword(){
		DynamicForm requestData = form().bindFromRequest();
		AuthorisedUser user = models.user.AuthorisedUser.findByEmail(session("connected"));		
		String currentPassoword = requestData.get("current");
		String newPassoword = requestData.get("new");
		String repeatPassoword = requestData.get("repeat");
		
		currentPassoword = AuthorisedUser.md5(currentPassoword);
		newPassoword = AuthorisedUser.md5(newPassoword);
		if(!newPassoword.equals(repeatPassoword)){
			flash("error","Ошибка при подтверждения пароля, попробуйте еще раз.");
			return ok(_passwordTab.render());
		}
		if(AuthorisedUser.find.where().eq("password",currentPassoword).findRowCount()==1){
			flash("success","Пароль успешно изменен!");
			user.password = newPassoword;
			user.update();
			return ok(_passwordTab.render());
		}
		else{
			flash("error","Проверьте правильность текущего пароля.");
			return ok(_passwordTab.render());
		}
	}
	
	public static Result changeUserSetting(Long id, String status){
		UserSetting userSetting = UserSetting.find.byId(id);
		userSetting.status = status;
		userSetting.update();
		return ok(_newsletterTab.render());
	}
	
	public static Result changeAdminSetting(Long id, String status){
		AdminSetting adminSetting = AdminSetting.find.byId(id);
		adminSetting.status = status;
		adminSetting.update();
		return ok(_newsletterTab.render());
	}
	
	public static Result notification(Object object, Boolean state){
		return TODO;
	}
	
	public static Result autoExtend(){
		return TODO;
	}
}
