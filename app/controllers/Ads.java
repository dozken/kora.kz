package controllers;

import static play.data.Form.form;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
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
import views.html.ad.create._breed;
import views.html.ad.create._city;
import views.html.ad.create.createAd;
import views.html.ad.edit.editAd;
import views.html.ad.search.adSearch;
import views.html.ad.show.*;


public class Ads extends Controller {

	public static Result get(Long id) {
		Ad ad = Ad.find.byId(id);
		++ad.views;
		ad.update();
		return ok(showAd.render(ad));
	}

	public static Result create() {

        DynamicForm requestData = form().bindFromRequest();
        System.out.println(requestData);
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
        ad.tags.add(addTag(ad,ad.animal.name));
        ad.tags.add(addTag(ad,ad.title));
        ad.tags.add(addTag(ad,ad.birthDate.toString()));
        ad.tags.add(addTag(ad,ad.breed.name));
        ad.tags.add(addTag(ad,ad.gender));
        ad.tags.add(addTag(ad,ad.quantity));
        ad.tags.add(addTag(ad,ad.contactInfo.region.name));
        ad.tags.add(addTag(ad,ad.contactInfo.city.name));
        ad.update();
        Ebean.saveManyToManyAssociations(ad, "tags");
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

    public static Tag addTag(Ad ad, String s){
        Tag tag = new Tag();
        tag.name = s;
        return tag;
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

        for(int i=0;i<ad.tags.size();i++){

            ad.tags.get(i).delete();
        }
        ad.tags.add(addTag(ad,ad.animal.name));
        ad.tags.add(addTag(ad,ad.title));
        ad.tags.add(addTag(ad,ad.description));
        ad.tags.add(addTag(ad,ad.birthDate.toString()));
        ad.tags.add(addTag(ad,ad.breed.name));
        ad.tags.add(addTag(ad,ad.gender));
        ad.tags.add(addTag(ad,ad.quantity));
        ad.tags.add(addTag(ad,ad.contactInfo.region.name));
        ad.tags.add(addTag(ad,ad.contactInfo.city.name));

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
		return ok(adSearch.render(Ad.find.all()));
	}

    public static Result favorite() {


        if(session("connected")!=null){

            return ok(adSearch.render(AuthorisedUser.findByEmail(session("connected")).favorites));

        }else{

            if( request().cookie("userAdsKora")==null)  return ok(adSearch.render(null));

            System.out.println(request().cookie("userAdsKora").value());
            String [] ads = request().cookie("userAdsKora").value().split("_");
            List<Ad> list = new ArrayList<Ad>();
            for(String ad : ads){
                if(!ad.equals("") && ad!=null)
                list.add(Ad.find.byId(Long.parseLong(ad)));

            }
            return ok(adSearch.render(list));
        }


    }

    public static Result sendPrivateMessage(Long ad_id, Long author_id)
    {
        DynamicForm requestData = form().bindFromRequest();
        System.out.println(requestData);

        AuthorisedUser user = AuthorisedUser.find.where().eq("email",Ad.find.byId(ad_id).contactInfo.email).findUnique();
        if(user!=null){
            System.out.println("registred");
            PrivateMessage message = new PrivateMessage();
            message.ad = Ad.find.byId(ad_id);
            message.author = AuthorisedUser.find.byId(author_id);
            message.message = requestData.get("message");
            message.recipent = user;
            message.status="unread";
            message.title = Ad.find.byId(ad_id).title;
            message.save();
        }else{
            System.out.println("not registred");
        }
        return ok();
    }

    public static Result replayPrivateMessage(Long author_id,Long ad_id, Long recipent_id,String m)
    {
        DynamicForm requestData = form().bindFromRequest();
        System.out.println(requestData);

        PrivateMessage message = new PrivateMessage();
        message.ad = Ad.find.byId(ad_id);
        message.author = AuthorisedUser.find.byId(author_id);
        message.message = m;
        message.recipent = AuthorisedUser.find.byId(recipent_id);;
        message.status="unread";
        message.title = "(re)"+Ad.find.byId(ad_id).title;
        message.save();

        return ok();
    }

    public static Result deleteMessages(String ids, String type){

        for(String id:ids.split("_")){
            if(!id.equals("") && id!=null){
                PrivateMessage message = PrivateMessage.find.byId(Long.parseLong(id));

                if(type.equals("send")){
                    if(!message.status.equals("recipent-deleted")) {message.status="author-deleted"; message.update();}
                    else message.delete();
                }else{
                    if(!message.status.equals("author-deleted")) {message.status="recipent-deleted"; message.update();}
                    else message.delete();
                }
            }

        }
        AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
        if(type.equals("send")) return ok(views.html.profile.messages._message.render(PrivateMessage.find.where().eq("author_id", u.id).ne("status", "author-deleted").order("send_date desc").findList()));

        return ok(views.html.profile.messages._message.render(PrivateMessage.find.where().eq("recipent_id", u.id).ne("status", "recipent-deleted").order("send_date desc").findList()));
    }

    public static Result readAsMessages(String ids){

        for(String id:ids.split("_")) {
            if (!id.equals("") && id != null) {
                PrivateMessage message = PrivateMessage.find.byId(Long.parseLong(id));
                message.status = "readed";
                message.update();
            }
        }
        AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
        return ok(views.html.profile.messages._message.render(PrivateMessage.find.where().eq("recipent_id", u.id).ne("status", "recipent-deleted").order("send_date desc").findList()));
    }

	public static Result addFavorite(Long id,String type){

        if(session("connected")!=null) {
            AuthorisedUser user = AuthorisedUser.findByEmail(session("connected"));

            if(type.equals("add")) {
                if (user.favorites == null) {
                    user.favorites = new ArrayList<Ad>();
                }
                user.favorites.add(Ad.find.byId(id));
            }else{

                user.favorites.remove(Ad.find.byId(id));
            }
            user.update();
        }else{
            String ids="";
            if(request().cookie("userAdsKora")!=null){ids=request().cookie("userAdsKora").value();}
            if(type.equals("add")) {
                ids += id + "_";
            }else{
                String b="";
                for(String s:ids.split("_")){
                    if(!s.equals(id.toString())){b+=s+"_";}
                }
                ids=b;
            }
            response().discardCookie("userAdsKora");
            response().setCookie("userAdsKora",ids,(86400*7));

        }

        return ok();
    }

    public static boolean checkFavorite(Long id){

        if(session("connected")!=null){

            AuthorisedUser user = AuthorisedUser.findByEmail(session("connected"));
            if(user.favorites.contains(Ad.find.byId(id))){return true;}return false;
        }else{

            if( request().cookie("userAdsKora")==null) return false;

            System.out.println(request().cookie("userAdsKora").value());
            String [] ads = request().cookie("userAdsKora").value().split("_");

            for(String ad : ads){
                if(ad.equals(id.toString())) return true;

            }
            return false;
        }

    }

    public static Result comment(Long id,Long com_id){

        DynamicForm requestData = form().bindFromRequest();
        System.out.println(requestData);
        Comment comment = new Comment();
        comment.ad = Ad.find.byId(id);
        comment.email = requestData.get("comment_email");
        comment.text = requestData.get("comment_message");
        comment.name = requestData.get("comment_author");
        if(com_id!=0){
            comment.coment = Comment.find.byId(com_id);
        }
        comment.save();

        return ok(_comment.render(Ad.find.byId(id).comments));
    }

}
