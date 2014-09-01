package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.ads.*;
import views.html.profile.messages.*;
import views.html.profile.payments.*;
import views.html.profile.settings.*;
import views.html.profile.info.*;

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
