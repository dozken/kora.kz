package controllers;

import static play.data.Form.form;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import models.Emailing;
import models.ad.Ad;
import models.ad.Comment;
import models.contact.City;
import models.user.AuthorisedUser;
import play.Play;
import play.Routes;
import play.api.libs.Crypto;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.common.about;
import views.html.common.feedback;
import views.html.common.rules;
import views.html.common.sitemap;
import views.html.mailBody.ad_successfully;
import views.html.mailBody.comment_users_ad;
import views.html.mailBody.feedbackMessage;
import views.html.mailBody.private_message;
import views.html.mailBody.registred_successfully;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class Application extends Controller {

	public static Result captcha() {
		DefaultKaptcha captchaPro = new DefaultKaptcha();
		captchaPro.setConfig(new Config(new Properties()));
		String text = captchaPro.createText();
		session(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, text);
		// Logger.debug("Captcha:" + text);// U can put the text in cache.
		BufferedImage img = captchaPro.createImage(text);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "jpg", baos);
			baos.flush();
		} catch (IOException e) {
			// Logger.debug(e.getMessage());
		}
		return ok(baos.toByteArray()).as("image/jpg");
	}

	public static WebSocket<JsonNode> socketHandler() {
		return WS.socket;
	}

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");

		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.Application.index(),
				controllers.routes.javascript.Application.signIn(),
				controllers.routes.javascript.Application.currency(),
				controllers.routes.javascript.User.register(),
				controllers.routes.javascript.Filters.getCategories(),
				controllers.routes.javascript.Filters.getParentCategories(),
				controllers.routes.javascript.Filters.addCategory(),
				controllers.routes.javascript.Filters.updateCategory(),
				controllers.routes.javascript.Filters.deleteCategory(),
				controllers.routes.javascript.Filters.addRegion(),
				controllers.routes.javascript.Filters.updateRegion(),
				controllers.routes.javascript.Filters.deleteRegion(),
				controllers.routes.javascript.Filters.getCities(),
				controllers.routes.javascript.Filters.getParentCities(),
				controllers.routes.javascript.Filters.addCity(),
				controllers.routes.javascript.Filters.updateCity(),
				controllers.routes.javascript.Filters.deleteCity(),
				controllers.routes.javascript.Settings.changePassword(),
				controllers.routes.javascript.Settings.changeUserSetting(),
				controllers.routes.javascript.Settings.changeAdminSetting(),
				controllers.routes.javascript.Advertisements.sessionAdvert(),
				controllers.routes.javascript.Advertisements.expand(),
				controllers.routes.javascript.Advertisements.add(),
				controllers.routes.javascript.Advertisements.remove(),
				controllers.routes.javascript.Advertisements.replace(),
				controllers.routes.javascript.Administer.moderate(),
				controllers.routes.javascript.Ads.getCategories(),
				controllers.routes.javascript.Ads.getCities(),
				controllers.routes.javascript.Ads.addFavorite(),
				controllers.routes.javascript.Ads.sendPrivateMessage(),
				controllers.routes.javascript.Ads.replayPrivateMessage(),
				controllers.routes.javascript.Ads.preLong(),
				controllers.routes.javascript.Ads.highlight(),
				controllers.routes.javascript.Ads.autoPreLong(),
				controllers.routes.javascript.Ads.searchAd(),
				controllers.routes.javascript.Ads.archive(),
				controllers.routes.javascript.Ads.get(),
				controllers.routes.javascript.Ads.comment(),
				controllers.routes.javascript.Ads.deleteMessages(),
				controllers.routes.javascript.Ads.readAsMessages(),
				controllers.routes.javascript.Ads.filterAds(),
				controllers.routes.javascript.Ads.favoriteSize(),
				controllers.routes.javascript.Manage.read(),
				controllers.routes.javascript.Manage.paymentReport(),
				controllers.routes.javascript.Manage.addMoney(),
				controllers.routes.javascript.Application.emailing(),
				controllers.routes.javascript.User.removeUser(),
				controllers.routes.javascript.User.removeModerator(),
				controllers.routes.javascript.Administer.removeAd(),
				controllers.routes.javascript.Administer.filterAllAds(),
				controllers.routes.javascript.Administer.filterToSession(),

				controllers.routes.javascript.Manage.getMessageType()));
	}

	// public static Result changeLanguage(String language) {
	// Controller.changeLang(language);
	// return redirect(request().getHeader("referer"));
	// }

	public static Result signIn() {

		DynamicForm requestData = form().bindFromRequest();
		System.out.println(Crypto.encryptAES(requestData.get("password")));
		AuthorisedUser user = AuthorisedUser.findByEmail(requestData
				.get("email"));
		System.out.println(user.password);
		if (user != null && user.password.equals(Crypto.encryptAES(requestData.get("password")))
				&& user.status.equals("active")) {
			session("connected", user.email);
			flash("thank you");
			changeLang("ru");
			return redirect(request().getHeader("referer"));
		} else if (user != null
				&& user.password.equals(Crypto.encryptAES(requestData.get("password")))
				&& !user.status.equals("active")) {
			return ok("inactive");

		} else {
			return ok("error");

		}
	}

	public static Result signOut() {
		session().clear();
		session().clear();
		flash("thank you");
		return redirect(routes.Application.index());
	}

	public static Result signUp() {
		return redirect(request().getHeader("referer"));
	}

	public static Result index() throws IOException {

		return ok(views.html.common.index.render());
	}

	public static Result about() {
		return ok(about.render());
	}

	public static Result sitemap(Long id) {
		if(id==null)
		return ok(sitemap.render(null));
		else{return ok(sitemap.render(City.find.byId(id))); }
	}

	public static Result rules() {
		return ok(rules.render());
	}

	public static Result feedback() {
		return ok(feedback.render());
	}

	public static Result sendFeedback() {
		DynamicForm requestData = form().bindFromRequest();
		String kaptchaExpected = session(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String kaptchaReceived = requestData.get("kaptcha");
		String email = requestData.get("email");
		String text = requestData.get("text");
		if (kaptchaExpected.equalsIgnoreCase(kaptchaReceived)) {

			Emailing.send("Напишите нам", Play.application().configuration()
					.getString("support.email"),
					feedbackMessage.render(email, text).body());
			flash("success", "Сообщение успешно отправлено.");
			session().remove(
					com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			return redirect(request().getHeader("referer"));
		} else {
			flash("error", "Введите, пожалуйста, цифры с картинки.");
			flash("email", email);
			flash("text", text);
			return ok(feedback.render());
		}

	}

	public static Result emailing(String type, String email) {

		if (type.equals("ad_created")) {

			Emailing.send("Объявление принято", new String[] { "" + " <"
					+ email + ">" }, ad_successfully.render().body());

		} else if (type.equals("register")) {
			AuthorisedUser user = AuthorisedUser.findByEmail(email);

			Emailing.send("Қора.kz", new String[] { user.userName + " <"
					+ user.email + ">" }, registred_successfully.render(user)
					.body());
		} else if (type.equals("replay_message")) {
			AuthorisedUser user = AuthorisedUser.find.byId(Long
					.parseLong(email));
			Emailing.send("Қора.kz", new String[] { user.userName + " <"
					+ user.email + ">" }, private_message.render("", "replay")
					.body());
		} else if (type.equals("private")) {
			Ad ad = Ad.find.byId(Long.parseLong(email));

			AuthorisedUser user = AuthorisedUser
					.findByEmail(ad.contactInfo.email);
			Emailing.send("Қора.kz", new String[] { user.userName + " <"
					+ user.email + ">" },
					private_message.render("", "registred").body());
		} else if (type.equals("comment")) {
			Ad ad = Ad.find.byId(Long.parseLong(email));

			Emailing.send("Қора.kz", new String[] { ad.contactInfo.company
					+ " <" + ad.contactInfo.email + ">" }, comment_users_ad
					.render(ad.contactInfo.company, ad, "comment").body());
		} else if (type.equals("replayComment")) {
			Comment comment = Comment.find.byId(Long.parseLong(email));

			Emailing.send(
					"Қора.kz",
					new String[] { comment.name + " <" + comment.email + ">" },
					comment_users_ad.render(comment.name, comment.ad,
							"replayComment").body());
		}

		return ok();
	}

	public static Result currency(String str, Double rate) {

		if (rate != null) {
			session(str, rate.toString());
		}

		return ok();

	}
}
