package controllers;

import models.ad.Ad;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ad.create.createAd;
import views.html.ad.search.adSearch;
import views.html.ad.show.showAd;

import com.fasterxml.jackson.databind.node.ObjectNode;


public class Ads extends Controller {

	public static Result get(Long id) {
		return ok(showAd.render(Ad.find.byId(id)));
	}

	public static Result create() {
		Ad ad = new Ad();
		ObjectNode event = Json.newObject();
		event.put("pending","active");	
		ad.status = "pending";
		ad.update();
		event.put("id",ad.id);
		event.put("title",ad.title);
		event.put("company",ad.contactInfo.company);
		event.put("moderatingBy",models.user.AuthorisedUser.findByEmail(session("connected")).userName);
		WS.send(event);
		return ok(createAd.render());
	}
	
	public static Result search() {
		return ok(adSearch.render());
	}
	

	

}
