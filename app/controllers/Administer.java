package controllers;

import models.ad.Ad;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.ads.adminAds;
import views.html.admin.advertisements.adminAdvertisements;
import views.html.admin.filters.adminFilters;
import views.html.admin.moderators.adminModerators;
import views.html.admin.users.adminUsers;

import com.fasterxml.jackson.databind.node.ObjectNode;

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
	
	public static Result moderators(){
		return ok(adminModerators.render());
	}
	
	public static Result moderate(Long id){
		Ad ad = Ad.find.byId(id);
		ObjectNode event = Json.newObject();
		ad.status = "moderating";
		ad.update();
		event.put("moderating","active");
		event.put("id",ad.id);
		event.put("title",ad.title);
		event.put("company",ad.contactInfo.company);
		event.put("moderatingBy",models.user.AuthorisedUser.findByEmail(session("connected")).userName);
		WS.send(event);
		//return ok();
		return redirect(routes.Ads.get(id));
	}
}
