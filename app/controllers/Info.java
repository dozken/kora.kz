package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.info.edit.*;
public class Info extends Controller {
	
	public static Result edit(){
		return ok(myInfoEdit.render());
	}

}
