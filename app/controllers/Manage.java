package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.myAds.*;
import views.html.profile.myMessages.*;
import views.html.profile.myPayments.*;
import views.html.profile.mySettings.*;
import views.html.profile.myInfo.*;

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
