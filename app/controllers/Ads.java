package controllers;

import models.Location;
import models.ad.*;
import models.contact.City;
import models.contact.ContactInfo;
import models.contact.Region;
import models.user.AuthorisedUser;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.ad.create.*;
import views.html.ad.edit.*;
import views.html.ad.search.adSearch;
import views.html.ad.show.showAd;

import java.io.File;
import java.util.List;

import static play.data.Form.form;


public class Ads extends Controller {

	public static Result get(Long id) {
		Ad ad = Ad.find.byId(id);
		++ad.views;
		ad.update();
		return ok(showAd.render(ad));
	}

	public static Result create() {

        DynamicForm requestData = form().bindFromRequest();
        Ad ad = new Ad();
        ad.animal = Animal.find.byId(Long.parseLong(requestData.get("animal")));
        ad.breed = Breed.find.byId(Long.parseLong(requestData.get("breed")));
        ad.birthDate = Integer.parseInt(requestData.get("age"));

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.city = City.find.byId(Long.parseLong(requestData.get("city")));
        contactInfo.region = Region.find.byId(Long.parseLong(requestData.get("region")));
        contactInfo.phone = requestData.get("phone");
        contactInfo.company = requestData.get("company_name");
        contactInfo.email = requestData.get("email");

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

        contactInfo.location = location;

        ad.contactInfo = contactInfo;
        ad.description = requestData.get("description");
        ad.quantity = requestData.get("quantity");
        ad.title = requestData.get("title");
        ad.gender = requestData.get("animal_gender");

        Price price=new Price();
        if(requestData.get("payment_type").equals("normal")){

            price.currency = requestData.get("currency");
            price.price = requestData.get("money");
        }else{price.price = requestData.get("payment_type");}
        price.save();
        ad.priceType = price;
        ad.save();

        String[] order = requestData.get("image_names").split("&");
        for(int i=0;i<order.length;i++){

            if(!order[i].equals("") && order[i]!=null){

                AdImage img = new AdImage();
                img.ad = ad;
                img.name = order[i];
                img.position =i+1;
                img.content = requestData.get(order[i]);
                img.save();
            }
        }

        //saveImages(ad,body.getFiles(),requestData.get("image_order"));

		return ok();
	}

    public static Result update(Long id) {

        DynamicForm requestData = form().bindFromRequest();

        System.out.println(requestData.get("age") + "  " + requestData.get("payment_type"));
        Ad ad = Ad.find.byId(id);
        ad.animal = Animal.find.byId(Long.parseLong(requestData.get("animal")));
        ad.breed = Breed.find.byId(Long.parseLong(requestData.get("breed")));
        ad.birthDate = Integer.parseInt(requestData.get("age"));


        ad.contactInfo.city = City.find.byId(Long.parseLong(requestData.get("city")));
        ad.contactInfo.region = Region.find.byId(Long.parseLong(requestData.get("region")));
        ad.contactInfo.phone = requestData.get("phone");
        ad.contactInfo.company = requestData.get("company_name");
        ad.contactInfo.email = requestData.get("email");

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
            ad.contactInfo.location = location;
        }

        ad.description = requestData.get("description");
        ad.quantity = requestData.get("quantity");
        ad.gender = requestData.get("animal_gender");
        ad.title = requestData.get("title");

        ad.priceType.currency = "";
        if(requestData.get("payment_type").equals("normal")){

            ad.priceType.currency = requestData.get("currency");
            ad.priceType.price = requestData.get("money");
        }else{ad.priceType.price = requestData.get("payment_type");}

        ad.update();
        String[] order = requestData.get("image_names").split("&");

        for(int i=0;i<ad.images.size();i++){

            ad.images.get(i).delete();
        }

        for(int i=0;i<order.length;i++){

            if(!order[i].equals("") && order[i]!=null){

                AdImage img = new AdImage();
                img.ad = ad;
                img.name = order[i];
                img.position =i+1;
                img.content = requestData.get(order[i]);
                img.save();
            }
        }



       // saveImages(ad,body.getFiles(),requestData.get("image_order"));

        return ok();
    }

    public static Result getBreeds(Long id){return ok(_breed.render(Animal.find.byId(id)));}

    public static Result getCities(Long id){return ok(_city.render(Region.find.byId(id)));}


    public static Result show(){
        if(session("connected")!=null) {
            return ok(createAd.render(AuthorisedUser.findByEmail(session("connected"))));
        }
        return ok(createAd.render(null));
    }

    public static Result edit(Long id){ return ok(editAd.render(Ad.find.byId(id))); }
	
	public static Result search() {
		return ok(adSearch.render());
	}
	

	

}
