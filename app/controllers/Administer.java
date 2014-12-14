package controllers;

import static play.data.Form.form;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import models.Emailing;
import models.ad.Ad;
import models.user.AuthorisedUser;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.ads.adminAds;
import views.html.admin.advertisements.adminAdvertisements;
import views.html.admin.filters.adminFilters;
import views.html.admin.moderators.adminModerators;
import views.html.admin.users.adminUsers;
import views.html.mailBody.adModerate;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Restrict(@Group("admin"))
public class Administer extends Controller {

	@Restrict({ @Group("moderator"), @Group("admin") })
	public static Result ads() {
		return ok(adminAds.render());
	}

	public static Result allAds(int page, String sort)
	{
		return ok(views.html.admin.allAds.adminAds.render(Ad.find.where().order(sort).findPagingList(20).getPage(page)
				.getList(),page,sort,Ad.find.where().findRowCount()));
	}

	public static Result removeAd(Long id, int page, String sort) {
		String a = "delete from users_ads where ads_id=" + id;
		SqlUpdate update = Ebean.createSqlUpdate(a);
		Ebean.execute(update);

		Ad ad = Ad.find.byId(id);
		ad.delete();
		return ok(views.html.admin.allAds.adminAds.render(Ad.find.where().order(sort).findPagingList(20).getPage(page)
				.getList(),page,sort,Ad.find.where().findRowCount()));
	}

	public static Result filterToSession(String name, String status,int page, String sort) {
		session(name, status);
		return ok(views.html.admin.allAds._ad.render(Ad.find.where().order(sort).findPagingList(20).getPage(page)
				.getList(),page,sort));
	}

	public static Result filterAllAds(int page, String sort) {
		return ok(views.html.admin.allAds._ad.render(Ad.find.where().order(sort).findPagingList(20).getPage(page)
				.getList(),page,sort));
	}

	public static Result filters() {
		return ok(adminFilters.render());
	}

	public static Result advetiments() {
		return ok(adminAdvertisements.render());
	}

	public static Result users(int page, String sort) {
		return ok(adminUsers.render(AuthorisedUser.getUsers(page, sort), page, sort,AuthorisedUser.getUsersCount()));
	}

	public static Result moderators(int page, String sort) {

		return ok(adminModerators.render(AuthorisedUser.getModerators(page, sort), page, sort,AuthorisedUser.getModeratorsCount()));
	}

	@Restrict({ @Group("admin"), @Group("moderator") })
	public static Result moderate(Long id) {
		Ad ad = Ad.find.byId(id);
		DynamicForm requestData = form().bindFromRequest();
		ad.status = requestData.get("status");
		ad.update();

		/**
		 * moderating active rejected
		 */
		if (ad.status.equals("active") || ad.status.equals("rejected")) {
			Emailing.send("Қора.kz: Ваше объявление",
					new String[] { ad.contactInfo.company + " <"
							+ ad.contactInfo.email + ">" },
					adModerate.render(ad).body());
		}
		if (requestData.get("status").equals("moderating")) {
			ObjectNode event = Json.newObject();
			event.put("moderating", "active");
			event.put("id", ad.id);
			if (ad.section.id != 5) {
				event.put("title", ad.category.name);
			} else {
				event.put("title", ad.title);
			}
			event.put("company", ad.contactInfo.company);
			event.put("moderatingBy", models.user.AuthorisedUser
					.findByEmail(session("connected")).userName);
			WS.send(event);
		}
		// return ok();
		return redirect(routes.Ads.get(id));
	}
}
