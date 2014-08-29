package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.ads.myAds;
import views.html.profile.info.myInfo;
import views.html.profile.messages.myMessages;
import views.html.profile.payments.myPayments;
import views.html.profile.settings.mySettings;

public class Manage extends Controller {
	
	public static Result myAds(){
		return ok(myAds.render());
	}
	
	public static Result myMessages(){
		return ok(myMessages.render());
	}
	
	public static Result myPayments(){
		return ok(myPayments.render());
	}
	
	public static Result mySettings(){
		return ok(mySettings.render());
	}
	
	public static Result myInfo(){
		return ok(myInfo.render());
	}
	
	

}
