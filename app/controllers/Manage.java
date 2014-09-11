package controllers;

import com.avaje.ebean.Expr;
import models.ad.Ad;
import models.ad.PrivateMessage;
import models.user.AuthorisedUser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.ads.myAds;
import views.html.profile.info.myInfo;
import views.html.profile.messages.myMessages;
import views.html.profile.messages._read;
import views.html.profile.messages._message;
import views.html.profile.payments.myPayments;
import views.html.profile.settings.mySettings;
import be.objectify.deadbolt.java.actions.SubjectPresent;

import java.util.List;

@SubjectPresent
public class Manage extends Controller {

	public static Result myAds() {
		return ok(myAds.render(Ad.find.all()));
	}



	public static Result myMessages() {

        AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));

        List<PrivateMessage> mes = PrivateMessage.find.where().eq("recipent_id",u.id).order("send_date desc").findList();
		return ok(myMessages.render(mes));
	}

    public static Result getMessageType(String s){

        AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
        System.out.println(s);

        if(s.equals("all")) return ok(_message.render(PrivateMessage.find.where().or(Expr.eq("recipent_id",u.id),Expr.eq("author_id",u.id)).order("send_date desc").findList()));

        if(s.equals("send")) return ok(_message.render(PrivateMessage.find.where().eq("author_id",u.id).order("send_date desc").findList()));

        return ok(_message.render(PrivateMessage.find.where().eq("recipent_id",u.id).order("send_date desc").findList()));
    }
    public static Result read(Long id){

        PrivateMessage message = PrivateMessage.find.byId(id);
        message.status = "readed";
        message.update();
        System.out.println("sdf");
        return ok(_read.render(message));
    }

	public static Result myPayments() {
		return ok(myPayments.render());
	}

	public static Result mySettings() {
		return ok(mySettings.render());
	}

	public static Result myInfo() {
		return ok(myInfo.render(AuthorisedUser
				.findByEmail(session("connected"))));
	}

	public static Result saveProfile() {

		return ok();
	}

	public static Result editProfile() {

		return ok();
	}

}
