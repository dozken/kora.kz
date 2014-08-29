package controllers;

import models.ad.Ad;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ad.create.createAd;
import views.html.ad.search.adSearch;
import views.html.ad.show.showAd;


public class Ads extends Controller {

	public static Result get(Long id) {
		return ok(showAd.render(Ad.find.byId(id)));
	}

	public static Result create() {
		return ok(createAd.render());
	}
	
	public static Result search() {
		return ok(adSearch.render());
	}
	

}
