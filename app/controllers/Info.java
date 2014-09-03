package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.ning.http.util.Base64;
import models.Locations;
import models.ad.Image;
import models.contact.Region;
import models.user.AuthorisedUser;
import models.user.SocialNetwork;
import models.user.UserSocials;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.profile.info.edit.myInfoEdit;

import java.io.File;
import java.util.List;

import static play.data.Form.form;

public class Info extends Controller {
	
	public static Result edit(){


		return ok(myInfoEdit.render( AuthorisedUser.findByEmail(session("connected"))));
	}

    public static Result update(Long id) {

        play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();

        DynamicForm requestData = form().bindFromRequest();

        AuthorisedUser user = AuthorisedUser.find.byId(id);

        System.out.println(requestData);
        try{

            for (int i=0;i<body.getFiles().size();i++){

                Http.MultipartFormData.FilePart picture = body.getFiles().get(i);

                if (picture != null) {

                    File file = picture.getFile();
                    Image icon = new Image();
                    byte[] ima = com.google.common.io.Files.toByteArray(file);
                    icon.contentType = picture.getContentType();
                    String encodedImage = Base64.encode(ima);
                    icon.content = encodedImage;
                    icon.save();
                    user.profile.image = icon;
                }
            }

            user.profile.region = Region.find.byId(Long.parseLong(requestData.get("region")));

            user.userSocials.add(fillSocilal("vk",requestData.get("vk")));
            user.userSocials.add(fillSocilal("facebook",requestData.get("facebook")));
            user.userSocials.add(fillSocilal("website",requestData.get("website")));
            user.userSocials.add(fillSocilal("skype",requestData.get("skype")));

            user.profile.phone = requestData.get("phone");
            if(user.locations != null){

                String[] loc = requestData.get("location").substring(1,requestData.get("location").length()-1).split(",");

                if(loc.length==2) {


                    user.locations.lat = loc[0].trim();
                    user.locations.lng = loc[1].trim();

                }

            }else{

                String[] loc = requestData.get("location").substring(1,requestData.get("location").length()-1).split(",");

                 if(loc.length==2) {

                     Locations locations = new Locations();
                     locations.lat = loc[0].trim();
                     locations.lng = loc[1].trim();
                     user.locations = locations;
                 }
            }

            user.profile.address = requestData.get("address");
            user.profile.description = requestData.get("description");
            user.profile.gender = requestData.get("gender");
            user.userName = requestData.get("company_name");

            user.update();
            return ok("success");//renderImage(icon.id);
        }catch (Exception e) {
            System.out.println(e);
        }
        return ok("baaaaa");
    }

    public static UserSocials fillSocilal(String name, String value){

        UserSocials socials = new UserSocials();
        socials.socialNetwork = SocialNetwork.find.where().eq("name",name).findUnique();
        socials.value=value;
        return socials;

    }

    public static String getSocialByName(Long id, String name) {

        String sql = "select value from socials_user where user_id=" + id.toString() + " and social_network_id in (select id from social_network where name='" + name + "')";

        SqlQuery sqlQuery = Ebean.createSqlQuery(sql);

        // sqlQuery.setParameter("asd", a);

        // execute the query returning a List of MapBean objects
        List<SqlRow> list = sqlQuery.findList();

        if(list.size()>0){
                 return myTrim(list.get(0).values().toString());
        }
        return "";
    }

    public static String myTrim(String a) {

        String b = "";
        for (int i = 1; i < a.length() - 1; i++) {
            b += a.charAt(i);

        }

        return b;
    }
}
