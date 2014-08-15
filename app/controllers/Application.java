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

public class Application extends Controller {

    public static Result index() {
    	return ok(index.render(
            ""
            //models.user.AuthorisedUser.findByUserName("steve")
            )
            );
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

}
