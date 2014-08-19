package controllers;

import models.user.AuthorisedUser;
import play.*;
import play.mvc.*;
import views.html.*;
import play.*;
import play.mvc.*;

import views.html.*;
import views.html.ad.*;
import views.html.adlist.*;
import views.html.item.*;
import views.html.profile.*;
import views.html.myMessages.*;
import views.html.myMoney.*;
import views.html.mySettings.*;
import views.html.myProfile.*;
import views.html.myProfileEdit.*;
import views.html.adminPage.*;
import views.html.addFilter.*;
import views.html.addReklam.*;
import views.html.adminMessage.*;
import views.html.adminProfile.*;
import views.html.adminProfileEdit.*;
import views.html.users.*;
import views.html.about.*;
import views.html.read.*;
import views.html.adminRead.*;
import views.html.feedback.*;

public class Application extends Controller {

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");

		return ok(Routes.javascriptRouter("myJsRoutes",
				controllers.routes.javascript.Application.index()));
	}

	// public static Result changeLanguage(String language) {
	// Controller.changeLang(language);
	// return redirect(request().getHeader("referer"));
	// }

	public static Result signIn() {

		AuthorisedUser user = AuthorisedUser
				.findByEmail("info@technovision.kz");
		session("connected", user.email);
		flash("thank you");
		return redirect(request().getHeader("referer"));
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
		return ok(index.render("Главная"));
	}

	public static Result createAd() {
		return ok(createAd.render("Подать объявление"));
	}

	public static Result adList() {
		return ok(adlist.render());
	}

	public static Result item() {
		return ok(item.render());
	}

	public static Result profile() {
		return ok(profile.render());
	}

	public static Result myMessages() {
		return ok(myMessages.render());
	}

	public static Result myMoney() {
		return ok(myMoney.render());
	}

	public static Result mySettings() {
		return ok(mySettings.render());
	}

	public static Result myProfile() {
		return ok(myProfile.render());
	}

	public static Result myProfileEdit() {
		return ok(myProfileEdit.render());
	}
     public static Result adminPage() {
        return ok(adminPage.render());
    }

     public static Result addFilter() {
        return ok(addFilter.render());
    }
     public static Result addReklam() {
        return ok(addReklam.render());
    }
     public static Result adminMessage() {
        return ok(adminMessage.render());
    }
     public static Result adminProfile() {
        return ok(adminProfile.render());
    }
     public static Result adminProfileEdit() {
        return ok(adminProfileEdit.render());
    }
     public static Result users() {
        return ok(users.render());
    }
     public static Result about() {
        return ok(about.render());
    }
     public static Result read() {
        return ok(read.render());
    }
     public static Result adminRead() {
        return ok(adminRead.render());
    }
     public static Result feedback() {
        return ok(feedback.render());
    }
}
