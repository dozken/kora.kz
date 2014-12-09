import static play.mvc.Results.notFound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.Setting;
import models.ad.Animal;
import models.ad.Breed;
import models.ad.Price;
import models.admin.AdminSetting;
import models.contact.City;
import models.contact.Region;
import models.user.AuthorisedUser;
import models.user.Profile;
import models.user.SecurityRole;
import models.user.SocialNetwork;
import models.user.UserSetting;
import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application application) {

		if (Setting.find.findRowCount() == 0) {
			Map<Integer, String> userSettings = new TreeMap<Integer, String>();
			userSettings.put(1, "Получать рассылку сайта и уведомления?");
			userSettings.put(2,
					"Получать уведомления о новых сообщениях на почту?");
			userSettings.put(3,
					"Получать уведомления о новых комментариях на почту?");
			for (Map.Entry<Integer, String> entry : userSettings.entrySet()) {
				Setting setting = new Setting();
				setting.position = entry.getKey();
				setting.category = "user";
				setting.name = entry.getValue();
				setting.save();
			}

			Map<Integer, String> adminSettings = new TreeMap<Integer, String>();
			adminSettings.put(1, "Включить функцию \"Автопродение?\"");
			adminSettings.put(2, "Включить функцию \"Выделить объявление?\"");
			adminSettings.put(3, "Включить функцию \"Похожие объявления?\"");
			adminSettings.put(4, "Включить функцию \"Горячие объявления?\"");
			for (Map.Entry<Integer, String> entry : adminSettings.entrySet()) {
				Setting setting = new Setting();
				setting.position = entry.getKey();
				setting.category = "admin";
				setting.name = entry.getValue();
				setting.save();
			}

			Map<Integer, String> adSettings = new TreeMap<Integer, String>();

			adSettings.put(1, "prelong");
			adSettings.put(2, "highlight");
			for (Map.Entry<Integer, String> entry : adSettings.entrySet()) {
				Setting setting = new Setting();
				setting.position = entry.getKey();
				setting.category = "ad";
				setting.name = entry.getValue();
				setting.price = 500.0;
				setting.save();
			}

		}

		if (SecurityRole.find.findRowCount() == 0) {
			for (String name : Arrays.asList("admin", "moderator", "user")) {
				SecurityRole role = new SecurityRole();
				role.name = name;
				role.save();
			}
		}

		if (SocialNetwork.find.findRowCount() == 0) {
			for (String name : Arrays.asList("vk", "facebook", "skype",
					"website")) {
				SocialNetwork tmp = new SocialNetwork();
				tmp.name = name;
				tmp.save();
			}
		}

		if (Animal.find.findRowCount() == 0) {
			for (String name : Arrays.asList("Лошадь", "Верблюд", "Корова",
					"Овцы/Козы", "Другие")) {
				Animal animal = new Animal();
				animal.name = name;
				animal.save();
			}
		}

		if (Breed.find.findRowCount() == 0) {
			for (String name : Arrays.asList("Ахалтеке", "Арабский скакун",
					"Император")) {
				Breed breed = new Breed();
				breed.animal = Animal.find.where().eq("name", "Лошадь")
						.findUnique();
				breed.name = name;
				breed.save();
			}
		}

		if (Region.find.findRowCount() == 0) {
			for (String name : Arrays.asList("Акмолинская область",
					"Актюбинская область", "Западно-Казахстанская область")) {
				Region region = new Region();
				region.name = name;
				region.save();
			}
		}

		if (City.find.findRowCount() == 0) {
			for (String name : Arrays.asList("Астана")) {
				City city = new City();
				city.region = Region.find.where()
						.eq("name", "Акмолинская область").findUnique();
				city.name = name;
				city.save();
			}
		}

		if (Price.find.findRowCount() == 0) {
			for (Integer name : Arrays.asList(-2, -1)) {
				Price price = new Price();
				price.price = name;
				price.save();
			}

		}

		if (AuthorisedUser.find.findRowCount() == 0) {
			AuthorisedUser user = new AuthorisedUser();
			user.userName = "ТОО ҚОРА";
			user.email = "admin@kora.kz";
			user.password = "admin1234";
			user.status = "active";
			user.roles = new ArrayList<SecurityRole>();
			user.roles.add(SecurityRole.findByName("admin"));
			user.roles.add(SecurityRole.findByName("user"));
			List<UserSetting> userSettings = new ArrayList<UserSetting>();
			for (Setting setting : Setting.findByCategory("user")) {
				UserSetting userSetting = new UserSetting();
				userSetting.user = user;
				userSetting.setting = setting;
				userSetting.status = "on";
				userSettings.add(userSetting);
			}
			List<AdminSetting> adminSettings = new ArrayList<AdminSetting>();
			for (Setting setting : Setting.findByCategory("admin")) {
				AdminSetting adminSetting = new AdminSetting();
				adminSetting.user = user;
				adminSetting.setting = setting;
				adminSetting.status = "on";
				adminSettings.add(adminSetting);
			}
			user.userSettings = userSettings;
			user.adminSettings = adminSettings;

			Profile profile = new Profile();
			profile.address = "Жас орда";
			profile.description = "Очень клевый";
			profile.gender = "Муржской";
			profile.phone = "77077539587";
			profile.region = Region.find.where().eq("name", "Алматы")
					.findUnique();
			profile.user = user;
			user.profile = profile;

			user.save();
			Ebean.saveManyToManyAssociations(user, "roles");
			Ebean.saveManyToManyAssociations(user, "permissions");
		}

	}

	public Promise<Result> onHandlerNotFound(RequestHeader request) {
		return Promise.<Result> pure(notFound(views.html.notFoundPage
				.render(request.uri())));
	}

	// public Promise<Result> onError(RequestHeader request, Throwable t) {
	// Logger.error("Exception with onError", t);
	// return Promise.<Result> pure(internalServerError(views.html.errorPage
	// .render(t)));
	// }
}
