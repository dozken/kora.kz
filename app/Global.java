import java.util.ArrayList;
import java.util.Arrays;

import models.user.AuthorisedUser;
import models.user.SecurityRole;
import models.user.UserPermission;

import com.avaje.ebean.Ebean;

import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {

	@Override
    public void onStart(Application application)
    {
        if (SecurityRole.find.findRowCount() == 0)
        {
            for (String name : Arrays.asList("foo", "bar", "hurdy", "gurdy"))
            {
                SecurityRole role = new SecurityRole();
                role.name = name;
                role.save();
            }
        }

        if (UserPermission.find.findRowCount() == 0)
        {
            UserPermission permission = new UserPermission();
            permission.value = "printers.edit";
            permission.save();
        }
        
        if (AuthorisedUser.find.findRowCount() == 0)
        {
            AuthorisedUser user = new AuthorisedUser();
            user.userName = "steve";
            user.roles = new ArrayList<SecurityRole>();
            user.roles.add(SecurityRole.findByName("foo"));
            user.roles.add(SecurityRole.findByName("bar"));
            user.permissions = new ArrayList<UserPermission>();
            user.permissions.add(UserPermission.findByValue("printers.edit"));

            user.save();
            Ebean.saveManyToManyAssociations(user,
                                             "roles");
            Ebean.saveManyToManyAssociations(user,
                                             "permissions");
        }
    }
	
	/**
	 * TODO
	 * tolko zaregestrirovanye mogut ostavlyat komenty
	 */
}
