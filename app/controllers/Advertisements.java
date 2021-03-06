package controllers;

import static play.data.Form.form;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.admin.Advertisement;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.admin.advertisements._advertisement;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

public class Advertisements extends Controller {

	public static Result renderAdvetisement(Long id) {
		Advertisement advertisement = Advertisement.find.byId(id);
		return ok(advertisement.file).as(advertisement.fileType);
	}

	public static Result sessionAdvert(String id, String index) {
		session(id, index);
		return ok();
	}

	// @Restrict(@Group("admin"))
	public static Result expand(Long id) {
		DynamicForm advertisementForm = form().bindFromRequest();
		Advertisement advertisement = Advertisement.find.byId(id);

		try {
			advertisement.updateDate = advertisement.tillToDate;
			advertisement.tillToDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse(advertisementForm.get("tillToDate"));
		} catch (ParseException e) {
			Logger.error("Exception with parsing advert date", e);
			// TODO Auto-generated catch block
		}
		advertisement.update();
		return ok(_advertisement.render());
	}

	@Restrict(@Group("admin"))
	public static Result add() {
		MultipartFormData body = request().body().asMultipartFormData();
		DynamicForm advertisementForm = form().bindFromRequest();

		Advertisement advertisement = new Advertisement();
		advertisement.company = advertisementForm.get("company");
		advertisement.placeOnPage = advertisementForm.get("placeOnPage");
		try {
			advertisement.publishDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse(advertisementForm.get("publishDate"));
			advertisement.tillToDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse(advertisementForm.get("tillToDate"));
		} catch (ParseException e) {
			Logger.error("Exception with parsing advert date", e);
		}

		FilePart picture = body.getFile("embeddedObject");
		if (picture != null) {
			File file = picture.getFile();
			try {
				byte[] image = com.google.common.io.Files.toByteArray(file);
				advertisement.file = image;
				advertisement.fileType = picture.getContentType();
			} catch (IOException e) {
				Logger.error("Exception with convertign image to bytes", e);
				// TODO Auto-generated catch block
			}

			Advertisement.addPosition();
			advertisement.save();
			flash("success", "Реклама от <strong>" + advertisement.company
					+ "</strong> добавлена!");
			return ok(_advertisement.render());
		}
		return ok(_advertisement.render());

	}

	@Restrict(@Group("admin"))
	public static Result remove(Long id) {
		Advertisement advertisement = Advertisement.find.byId(id);
		Advertisement.removePosition(advertisement);
		advertisement.delete();
		flash("success", "Реклама от <strong>" + advertisement.company
				+ "</strong> удалена!");
		return ok(_advertisement.render());
	}

	@Restrict(@Group("admin"))
	public static Result replace(Long id, String direction) {
		Advertisement advertisement = Advertisement.find.byId(id);
		// if(direction.equals("up"))
		// flash("success", "Реклама от <strong>" + advertisement.company+
		// "</strong> перемещена в верх!");
		// if(direction.equals("down"))
		// flash("success", "Реклама от <strong>" + advertisement.company+
		// "</strong> перемещена в низ!");
		Advertisement.rePosition(advertisement, direction);
		return ok(_advertisement.render());
	}
}
