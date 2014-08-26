package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.ad.create.*;
import views.html.ad.show.*;
import views.html.ad.search.*;


public class Ads extends Controller {

	public static Result get() {
		return ok(showAd.render());
	}

	public static Result create() {
		return ok(createAd.render());
	}
	
	public static Result search() {
		return ok(adSearch.render());
	}
	

}
