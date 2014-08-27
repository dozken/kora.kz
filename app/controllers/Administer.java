package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.ads.*;
import views.html.admin.filters.*;
import views.html.admin.advertisements.*;
import views.html.admin.users.*;

public class Administer extends Controller {
	
	public static Result ads(){
		return ok(adminAds.render());
	}
	
	public static Result filters(){
		return ok(adminFilters.render());
	}
	
	public static Result advetiments(){
		return ok(adminAdvertisements.render());
	}
	
	public static Result users(){
		return ok(adminUsers.render());
	}
	
}
