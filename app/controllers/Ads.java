package controllers;

import models.Location;
import models.ad.*;
import models.contact.City;
import models.contact.ContactInfo;
import models.contact.Region;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.ad.create.*;
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

    public static Integer calculatePosition(String name,String order){

        name=name.replace(".","");

        System.out.println(order);
        order = order.replace("item[]=","");
        System.out.println(order);
        String[] orders = order.split("&");
        for(int i=0;i<orders.length;i++){
            if(orders[i].equals(name)){

                return (i+1);
            }
        }



        return 0;
    }

    public static void saveImages(Ad ad,List<Http.MultipartFormData.FilePart> files,String order){

try {
    for (int i = 0; i < files.size(); i++) {
        Http.MultipartFormData.FilePart picture = files.get(i);

        if (picture != null) {

            File file = picture.getFile();
            AdImage icon = new AdImage();
            byte[] ima = com.google.common.io.Files.toByteArray(file);
            icon.name = picture.getFilename();
            icon.contentType = picture.getContentType();
            icon.content = ima;
            icon.position = calculatePosition(picture.getFilename(),order);
            icon.ad = ad;
            icon.save();
        }
    }
}catch (Exception e){

}
    }

	public static Result create() {


        play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();

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
        contactInfo.email = requestData.get("company_name");

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

        Price price=new Price();
        if(requestData.get("payment_type").equals("normal")){

            price.currency = requestData.get("currency");
            price.price = requestData.get("money");
        }else{price.price = requestData.get("payment_type");}
        price.save();
        ad.priceType = price;
        ad.save();


        saveImages(ad,body.getFiles(),requestData.get("image_order"));

		return ok();
	}

    public static Result getBreeds(Long id){return ok(_breed.render(Animal.find.byId(id)));}

    public static Result getCities(Long id){return ok(_city.render(Region.find.byId(id)));}


    public static Result show(){ return ok(createAd.render()); }
	
	public static Result search() {
		return ok(adSearch.render());
	}
	

	

}
