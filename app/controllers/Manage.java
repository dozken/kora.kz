package controllers;

import java.util.List;

import models.ad.Ad;
import models.ad.PrivateMessage;
import models.user.AuthorisedUser;
import play.mvc.Controller;
import play.mvc.Result;
import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.avaje.ebean.Expr;

@SubjectPresent
public class Manage extends Controller {

	public static Result myAds() {
		return ok(myAds.render(Ad.find.all()));
	}



	public static Result myMessages() {

        AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));

        List<PrivateMessage> mes = PrivateMessage.find.where().eq("recipent_id",u.id).ne("status","recipent-deleted").order("send_date desc").findList();
		return ok(myMessages.render(mes));
	}

    public static Result getMessageType(String s){

        AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
        System.out.println(s);

        if(s.equals("all")) return ok(_message.render(PrivateMessage.find.where().or(Expr.eq("recipent_id",u.id),Expr.eq("author_id",u.id)).order("send_date desc").findList()));

        if(s.equals("send")) return ok(_message.render(PrivateMessage.find.where().eq("author_id",u.id).ne("status","author-deleted").order("send_date desc").findList()));

        return ok(_message.render(PrivateMessage.find.where().eq("recipent_id",u.id).ne("status","recipent-deleted").order("send_date desc").findList()));
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
