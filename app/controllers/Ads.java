package controllers;

import models.ad.Ad;
import models.ad.Animal;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ad.create._breed;
import views.html.ad.create.createAd;
import views.html.ad.search.adSearch;
import views.html.ad.show.showAd;

public class Ads extends Controller {

	public static Result get(Long id) {
		return ok(showAd.render(Ad.find.byId(id)));
	}

	public static Result create() {
		// Ad ad = new Ad();
		// ObjectNode event = Json.newObject();
		// event.put("pending","active");
		// ad.status = "pending";
		// //ad.update();
		// event.put("id",ad.id);
		// event.put("title",ad.title);
		// event.put("company",ad.contactInfo.company);
		// event.put("moderatingBy",models.user.AuthorisedUser.findByEmail(session("connected")).userName);
		// WS.send(event);

		return ok(createAd.render());
	}

	public static Result getBreeds(Long id) {
		System.out.println(id);
		return ok(_breed.render(Animal.find.byId(id)));
	}

	public static Result show() {
		return ok(createAd.render());
	}

	public static Result search() {
		return ok(adSearch.render());
	}

}
