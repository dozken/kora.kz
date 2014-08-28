package controllers;

import static play.data.Form.form;
import models.user.AuthorisedUser;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.settings._passwordTab;
public class Settings extends Controller {

	public static Result changePassword(){
		DynamicForm requestData = form().bindFromRequest();
		AuthorisedUser user = AuthorisedUser.find.byId(Long.parseLong(requestData.get("id")));		
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
	
	public static Result notification(Object object, Boolean state){
		return TODO;
	}
	
	public static Result autoExtend(){
		return TODO;
	}
}
