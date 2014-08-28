import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import models.ad.Ad;
import models.ad.Animal;
import models.ad.Breed;
import models.ad.Price;
import models.ad.Quantity;
import models.contact.ContactInfo;
import models.contact.Region;
import models.user.AuthorisedUser;
import models.user.Profile;
import models.user.SecurityRole;
import models.user.UserPermission;
import play.Application;
import play.GlobalSettings;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application application) {
		if (SecurityRole.find.findRowCount() == 0) {
			for (String name : Arrays.asList("admin", "moderator", "user")) {
				SecurityRole role = new SecurityRole();
				role.name = name;
				role.save();
			}
		}
		
		if (Animal.find.findRowCount() == 0) {
			for (String name : Arrays.asList("ЛОШАДЬ", "ВЕРБЛЮД", "КОРОВА","БАРАН","ДРУГИЕ")) {
				Animal animal = new Animal();
				animal.name = name;
				animal.save();
			}
		}
		
		if (Breed.find.findRowCount() == 0) {
			for (String name : Arrays.asList("АХАЛТЕКЕ", "АРАБСКИЙ СКАКУН", "ИМПЕРАТОР")) {
				Breed breed = new Breed();
				breed.animal = Animal.find.where().eq("name", "ЛОШАДЬ").findUnique();
				breed.name = name;
				breed.save();
			}
		}
		
		if (Region.find.findRowCount() == 0) {
			for (String name : Arrays.asList("Алматы", "Астана", "Шымкент")) {
				Region region = new Region();
				region.name = name;
				region.save();
			}
		}
		
		if (Quantity.find.findRowCount() == 0){
			for (String name : Arrays.asList("Один", "Косяк")) {
				Quantity quantity = new Quantity();
				quantity.name = name;
				quantity.save();
			}
		}
		
		if (Price.find.findRowCount() == 0){
			for (String name : Arrays.asList("На обмен", "Договорная цена")) {
				Price price = new Price();
				price.price = name;
				price.save();
			}
			
		}

		if (AuthorisedUser.find.findRowCount() == 0) {
			AuthorisedUser user = new AuthorisedUser();
			user.userName = "Technovision LTD";
			user.email = "info@technovision.kz";
			user.password = "19771977";
			user.roles = new ArrayList<SecurityRole>();
			user.roles.add(SecurityRole.findByName("admin"));
			
			Profile profile = new Profile();
			profile.address = "Жас орда";
			profile.description = "Очень клевый";
			profile.gender = "Муржской";
			profile.phone = "+77077539587";
			profile.region = Region.find.where().eq("name", "Алматы").findUnique();
			profile.user = user;
			user.profile = profile;

			user.save();
			Ebean.saveManyToManyAssociations(user, "roles");
			Ebean.saveManyToManyAssociations(user, "permissions");
		}
		
		if(Ad.find.findRowCount() == 0){
			Ad ad = new Ad();
			ad.animal = Animal.find.where().eq("name", "ЛОШАДЬ").findUnique();
			ad.breed =  Breed.find.where().eq("name", "АХАЛТЕКЕ").findUnique();
			ad.birthDate = new Date();
			ad.contactInfo = new ContactInfo(
					Region.find.where().eq("name", "Астана").findUnique(),
					"Technovisoin",
					"+77773332255",
					"email@mail.com",
					"map");
			ad.title = "Заголовок";
			ad.description = "Очень много текста. Ну очень много. Слишком много.";
			ad.quantity = Quantity.find.where().eq("name", "Косяк").findUnique();
			Price price = new Price();
			price.price = "100000";
			price.currency = "тг";
			price.save();
			ad.priceType = price;
			//TODO tags
			ad.save();
			
			
		}
	}

	/**
	 * TODO tolko zaregestrirovanye mogut ostavlyat komenty
	 */
}
