package controllers;

import static play.data.Form.form;
import controllers.routes;
import models.user.AuthorisedUser;
import models.user.SecurityRole;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.ads.*;
import views.html.admin.filters.*;
import views.html.admin.advertisements.*;
import views.html.admin.users.*;

import java.util.ArrayList;

public class User extends Controller {
	
	
	public static Result register() {

        DynamicForm requestData = form().bindFromRequest();

        System.out.println(requestData);

		if(AuthorisedUser.find.where().eq("email",requestData.get("email")).findUnique()==null){

            AuthorisedUser user = new AuthorisedUser();
            user.email = requestData.get("email");
            user.password = requestData.get("password");
            user.userName = requestData.get("nameOfUserOrCompany");
            SecurityRole role = SecurityRole.findByName("user");
            user.roles = new ArrayList<SecurityRole>();
            user.roles.add(role);
            user.save();
            return ok("success");
        }else
        {

            return ok("error");

        }

		//return redirect(routes.Manage.myInfo());
	}

//        return ok("gb");
//    }

}
