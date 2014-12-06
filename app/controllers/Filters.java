package controllers;

import static play.data.Form.form;
import models.Location;
import models.ad.Animal;
import models.ad.Breed;
import models.contact.City;
import models.contact.Region;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.filters.breed._breed;
import views.html.admin.filters.city._city;
import views.html.admin.filters.region._region;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

@Restrict(@Group("admin"))
public class Filters extends Controller {

	public static Result getCities(Long id) {
		return ok(_city.render(models.contact.Region.find.byId(id)));
	}

	public static Result addCity() {
		DynamicForm requestData = form().bindFromRequest();
		City city = new City();
		city.name = requestData.get("name");
		city.region = Region.find.ref(Long.parseLong(requestData
				.get("region.id")));
		String[] loc;
		String coords = requestData.get("location");
		if (requestData.get("location").startsWith("("))
			coords = requestData.get("location").substring(1,
					requestData.get("location").length() - 1);
		loc = coords.split(",");
		Location location = new Location();
		if (loc.length == 2) {
			location.lat = loc[0].trim();
			location.lng = loc[1].trim();
		}

		city.location = location;
		city.save();
		flash("success", "Город <strong>" + city.name + "</strong> добавлен!");
		return ok(_city.render(city.region));
		// }
	}

	public static Result updateCity(Long id, String name, String latlng) {
		City city = City.find.byId(id);
		String[] loc;
		String coords = latlng;
		if (latlng.startsWith("("))
			coords = latlng.substring(1, latlng.length() - 1);
		loc = coords.split(",");
		city.location.lat = loc[0].trim();
		city.location.lng = loc[1].trim();

		flash("success", "Город <strong>" + city.name
				+ "</strong> изменен на <strong>" + name + "</strong>!");
		city.name = name;
		city.update();
		return ok(_city.render(city.region));
		// }
	}

	public static Result deleteCity(Long id) {
		City city = City.find.byId(id);
		Region region = city.region;
		if (city.contactInfos != null && city.contactInfos.size() > 0) {
			flash("error",
					"Нельзя удалить, <strong>" + city.contactInfos.size()
							+ "</strong> контактов с городом <strong>"
							+ city.name + "</strong>!");
			return ok(_city.render(region));
		} else {
			flash("success", "Город <strong>" + city.name + "</strong> удален!");
			city.delete();
			return ok(_city.render(region));
		}
	}

	public static Result getBreeds(Long id) {
		return ok(_breed.render(models.ad.Animal.find.byId(id)));
	}

	public static Result addBreed() {
		Form<Breed> breedForm = form(Breed.class).bindFromRequest();
		flash().remove("error");

		if (breedForm.hasErrors()) {
			flash("error", "Не могу сохранить!");
			return badRequest();
		}
		/*
		if (Breed.find.where().eq("name", breedForm.get().name).findRowCount() > 0) {
			flash("error",
					"Нельзя сохранить, порода <strong>" + breedForm.get().name
							+ "</strong> уже существует!");
			return ok(_breed.render(breedForm.get().animal));
		}*/ else {
			Breed breed = breedForm.get();

			flash("success", "Порода <strong>" + breed.name
					+ "</strong> добавлена!");
			breed.save();
			return ok(_breed.render(breed.animal));
		}
	}

	public static Result updateBreed(Long id, String name) {
		Breed breed = Breed.find.byId(id);
		if (Breed.find.where().eq("name", name).findRowCount() > 0
				&& !breed.equals(Breed.find.where().eq("name", name)
						.findUnique())) {
			flash("error", "Нельзя обновить, порода <strong>" + name
					+ "</strong> уже существует!");
			return ok(_breed.render(breed.animal));
		} else {
			flash("success", "Порода <strong>" + breed.name
					+ "</strong> измененa на <strong>" + name + "</strong>!");
			breed.name = name;
			breed.update();
			return ok(_breed.render(breed.animal));
		}
	}

	public static Result deleteBreed(Long id) {
		Breed breed = Breed.find.byId(id);
		Animal animal = breed.animal;
		if (breed.ads != null && breed.ads.size() > 0) {
			flash("error", "Нельзя удалить, <strong>" + breed.ads.size()
					+ "</strong> объявления с породой <strong>" + breed.name
					+ "</strong>!");
			return ok(_breed.render(animal));
		} else {
			flash("success", "Порода <strong>" + breed.name
					+ "</strong> удаленa!");
			breed.delete();
			return ok(_breed.render(animal));
		}
	}

	/**
	 * 
	 * 
	 * */
	public static Result addRegion() {
		Form<Region> regionForm = form(Region.class).bindFromRequest();
		flash().remove("error");
		if (regionForm.hasErrors()) {
			flash("error", "Не могу сохранить!");
			return ok(_region.render());
		}
		if (Region.find.where().eq("name", regionForm.get().name)
				.findRowCount() > 0) {
			flash("error",
					"Нельзя сохранить, регион <strong>" + regionForm.get().name
							+ "</strong> уже существует!");
			return ok(_region.render());
		} else {
			Region region = regionForm.get();
			flash("success", "Регион <strong>" + region.name
					+ "</strong> добавлен!");
			region.save();
			return ok(_region.render());
		}
	}

	public static Result updateRegion(Long id, String name) {
		Region region = Region.find.byId(id);
		if (Region.find.where().eq("name", name).findRowCount() > 0
				&& !region.equals(Region.find.where().eq("name", name)
						.findUnique())) {
			flash("error",
					"Не могу обновить, уже есть регион с именем <strong>"
							+ name + "</strong>!");
			return ok(_region.render());
		} else {
			flash("success", "Регион <strong>" + region.name
					+ "</strong> изменен на <strong>" + name + "</strong>!");
			region.name = name;
			region.update();
			return ok(_region.render());
		}
	}

	public static Result deleteRegion(Long id) {
		Region region = Region.find.byId(id);
		if (region.contactInfos != null && region.contactInfos.size() > 0) {
			flash("error",
					"Нельзя удалить, <strong>" + region.contactInfos.size()
							+ "</strong> объявления с регионом <strong>"
							+ region.name + "</strong>!");
			return ok(_region.render());
		} else {
			flash("success", "Регион <strong>" + region.name
					+ "</strong> удален!");
			region.delete();
			return ok(_region.render());
		}

	}

}
