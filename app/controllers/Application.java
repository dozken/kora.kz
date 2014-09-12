package controllers;

import static play.data.Form.form;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import models.Emailing;
import models.user.AuthorisedUser;
import play.Play;
import play.Routes;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.common.about;
import views.html.common.feedback;
import views.html.mailBody.feedbackMessage;

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
				controllers.routes.javascript.User.register(),
				controllers.routes.javascript.Filters.getBreeds(),
				controllers.routes.javascript.Filters.addBreed(),
				controllers.routes.javascript.Filters.updateBreed(),
				controllers.routes.javascript.Filters.deleteBreed(),
				controllers.routes.javascript.Filters.addRegion(),
				controllers.routes.javascript.Filters.updateRegion(),
				controllers.routes.javascript.Filters.deleteRegion(),
				controllers.routes.javascript.Filters.getCities(),
				controllers.routes.javascript.Filters.addCity(),
				controllers.routes.javascript.Filters.updateCity(),
				controllers.routes.javascript.Filters.deleteCity(),
				controllers.routes.javascript.Settings.changePassword(),
				controllers.routes.javascript.Settings.changeUserSetting(),
				controllers.routes.javascript.Settings.changeAdminSetting(),
				controllers.routes.javascript.Advertisements.expand(),
				controllers.routes.javascript.Advertisements.add(),
				
				controllers.routes.javascript.Advertisements.remove(),
				controllers.routes.javascript.Advertisements.replace(),
				controllers.routes.javascript.Administer.moderate(),
				controllers.routes.javascript.Ads.getBreeds(),
				controllers.routes.javascript.Ads.getCities(),
                controllers.routes.javascript.Ads.addFavorite(),
                controllers.routes.javascript.Ads.sendPrivateMessage(),
                controllers.routes.javascript.Ads.replayPrivateMessage(),
				controllers.routes.javascript.Ads.get(),
				controllers.routes.javascript.Ads.comment(),
                controllers.routes.javascript.Ads.deleteMessages(),
                controllers.routes.javascript.Ads.readAsMessages(),

                controllers.routes.javascript.Manage.read(),
                controllers.routes.javascript.Manage.getMessageType()
                ));
	}

	// public static Result changeLanguage(String language) {
	// Controller.changeLang(language);
	// return redirect(request().getHeader("referer"));
	// }

	public static Result signIn() {
		DynamicForm requestData = form().bindFromRequest();
		AuthorisedUser user = AuthorisedUser.findByEmail(requestData
				.get("email"));
		if (user != null && user.password.equals(requestData.get("password"))) {
			session("connected", user.email);
			flash("thank you");
			changeLang("ru");
			return redirect(request().getHeader("referer"));
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

	public static Result index() {
		return ok(views.html.common.index.render());
	}

	public static Result about() {
		return ok(about.render());
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
}
