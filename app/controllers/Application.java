package controllers;

import models.user.AuthorisedUser;
import play.Routes;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.common.*;

import java.util.ArrayList;

import static play.data.Form.form;

public class Application extends Controller {

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");

		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.Application.index(),
				controllers.routes.javascript.User.register(),
                controllers.routes.javascript.Application.signIn()
				controllers.routes.javascript.Filters.getBreeds(),
				controllers.routes.javascript.Filters.addBreed(),
				controllers.routes.javascript.Filters.updateBreed(),
				controllers.routes.javascript.Filters.deleteBreed()
				));
	}

	// public static Result changeLanguage(String language) {
	// Controller.changeLang(language);
	// return redirect(request().getHeader("referer"));
	// }

	public static Result signIn() {

        DynamicForm requestData = form().bindFromRequest();

        System.out.println(requestData);

         AuthorisedUser user = AuthorisedUser.findByEmail(requestData.get("email"));
        if(user!=null && user.password.equals(requestData.get("password"))) {
            session("connected", user.email);
            flash("thank you");
            return redirect(request().getHeader("referer"));
        }else
        {
            return ok("error");

        }
    }

	public static Result signOut() {
		session().clear();
		flash("thank you");
		return redirect(routes.Application.index());
	}

	public static Result signUp() {
		return redirect(request().getHeader("referer"));
	}

	public static Result index() {
		return ok(views.html.common.index.render());
	}

	public static Result about() {
		return ok(about.render());
	}

	public static Result feedback() {
		return ok(feedback.render());
	}
	
}
