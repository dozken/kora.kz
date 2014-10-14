package controllers;

import static play.data.Form.form;
import models.ad.Ad;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.ads.adminAds;
import views.html.admin.advertisements.adminAdvertisements;
import views.html.admin.filters.adminFilters;
import views.html.admin.moderators.adminModerators;
import views.html.admin.users.adminUsers;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Restrict(@Group("admin"))
public class Administer extends Controller {

	@Restrict({ @Group("moderator"), @Group("admin") })
	public static Result ads() {
		return ok(adminAds.render());
	}

	public static Result filters() {
		return ok(adminFilters.render());
	}

	public static Result advetiments() {
		return ok(adminAdvertisements.render());
	}

	public static Result users() {
		return ok(adminUsers.render());
	}

	public static Result moderators() {
		return ok(adminModerators.render());
	}

	@Restrict({ @Group("admin"), @Group("moderator") })
	public static Result moderate(Long id) {
		Ad ad = Ad.find.byId(id);
		
		ad.status = "moderating";
		ad.update();
		if(ad.status.equals("moderating")){
		ObjectNode event = Json.newObject();
		event.put("moderating", "active");
		event.put("id", ad.id);
		event.put("title", ad.title);
		event.put("company", ad.contactInfo.company);
		event.put(
				"moderatingBy",
				models.user.AuthorisedUser.findByEmail(session("connected")).userName);
		WS.send(event);
		}
		// return ok();
		return redirect(routes.Ads.get(id));
	}
}
