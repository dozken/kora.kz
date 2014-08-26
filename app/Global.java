import java.util.ArrayList;
import java.util.Arrays;

import models.ad.Animal;
import models.user.AuthorisedUser;
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

//		if (UserPermission.find.findRowCount() == 0) {
//			// TODO
//			// add permissions
//			String permissions[] = { "ad.create" };
//			UserPermission permission = new UserPermission();
//			permission.value = "printers.edit";
//			permission.save();
//		}

		if (AuthorisedUser.find.findRowCount() == 0) {
			AuthorisedUser user = new AuthorisedUser();
			user.userName = "Technovision LTD";
			user.email = "info@technovision.kz";
			user.password = "19771977";
			user.roles = new ArrayList<SecurityRole>();
			user.roles.add(SecurityRole.findByName("admin"));
			user.permissions = new ArrayList<UserPermission>();
			//user.permissions.add(UserPermission.findByValue("printers.edit"));

			user.save();
			Ebean.saveManyToManyAssociations(user, "roles");
			Ebean.saveManyToManyAssociations(user, "permissions");
		}
	}

	/**
	 * TODO tolko zaregestrirovanye mogut ostavlyat komenty
	 */
}
