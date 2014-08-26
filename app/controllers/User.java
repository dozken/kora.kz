package controllers;

import static play.data.Form.form;
import controllers.routes;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.ads.*;
import views.html.admin.filters.*;
import views.html.admin.advertisements.*;
import views.html.admin.users.*;

public class User extends Controller {
	
	
	public static Result register(){
		
//		DynamicForm requestData = form().bindFromRequest();
		
//		System.out.println(requestData);
		return ok("baaa");
		//return redirect(routes.Manage.myInfo());
	}
	
}
