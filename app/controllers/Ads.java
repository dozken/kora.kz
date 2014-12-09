package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Emailing;
import models.Location;
import models.Setting;
import models.ad.Ad;
import models.ad.AdImage;
import models.ad.AdSetting;
import models.ad.Animal;
import models.ad.Breed;
import models.ad.Comment;
import models.ad.Price;
import models.ad.PrivateMessage;
import models.ad.Tag;
import models.contact.City;
import models.contact.ContactInfo;
import models.contact.Region;
import models.user.AuthorisedUser;
import models.user.Payment;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ad.create._breed;
import views.html.ad.create._city;
import views.html.ad.create.createAd;
import views.html.ad.edit.editAd;
import views.html.ad.search._ad_list;
import views.html.ad.search.adSearch;
import views.html.ad.show._comment;
import views.html.ad.show.showAd;
import views.html.mailBody.private_message;
import views.html.profile.ads.myAds;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

public class Ads extends Controller {

	public static Result get(Long id) {
		Ad ad = Ad.find.byId(id);
		++ad.views;
		try {
			ad.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok(showAd.render(ad));
	}

	public static Result create() {
		long startTime = System.currentTimeMillis();

		DynamicForm requestData = form().bindFromRequest();

		Ad ad = new Ad();
		ad.animal = Animal.find.byId(Long.parseLong(requestData.get("animal")));
		ad.breed = Breed.find.byId(Long.parseLong(requestData.get("breed")));
		ad.birthDate = Integer.parseInt(requestData.get("age"));
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.city = City.find.byId(Long.parseLong(requestData
				.get("city")));
		contactInfo.region = Region.find.byId(Long.parseLong(requestData
				.get("region")));
		contactInfo.phone = ContactInfo.phoneCorrect(requestData.get("phone"));
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
		Price price = new Price();
		if (requestData.get("payment_type").equals("normal")) {

			price.currency = requestData.get("currency");
			price.price = Integer.parseInt(requestData.get("money"));
		} else if (requestData.get("payment_type").equals("change")) {
			price.price = -2;
		} else {
			price.price = -1;
		}
		price.save();
		ad.priceType = price;

		AdSetting ad_set = new AdSetting();
		ad_set.name = "comment";
		ad_set.status = requestData.get("coment_display");

		AdSetting ad_set2 = new AdSetting();
		ad_set2.name = "autoprelong";
		ad_set2.status = "off";
		if (ad.settings == null) {
			ad.settings = new ArrayList<AdSetting>();
		}
		ad.settings.add(ad_set);
		ad.settings.add(ad_set2);
		ad.save();

		if (ad.settings == null) {
			ad.tags = new ArrayList<Tag>();
		}
		ad.tags.add(new Tag(ad.animal.name));
		ad.tags.add(new Tag(ad.breed.name));
		ad.tags.add(new Tag(ad.title));
		ad.tags.add(new Tag(ad.birthDate.toString()));
		ad.tags.add(new Tag(ad.gender));
		ad.tags.add(new Tag(ad.quantity));
		ad.tags.add(new Tag(ad.contactInfo.region.name));
		ad.tags.add(new Tag(ad.contactInfo.city.name));
		ad.update();

		Ebean.saveManyToManyAssociations(ad, "tags");
		String[] order = requestData.get("image_names").split("&");
		long image = System.currentTimeMillis() - startTime;
		System.out.println("image:" + image);
		for (int i = 0; i < order.length; i++) {

			if (!order[i].equals("") && order[i] != null) {

				AdImage img = new AdImage();
				img.ad = ad;
				img.name = order[i];
				img.position = i + 1;
				img.content = requestData.get(order[i]);
				new Thread() {
					public void run() {
						img.save();
					}
				}.start();
			}
		}
		// ... do something ...
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("estimatedTime:" + estimatedTime);
		return ok();
	}

	public static Result filterAds(String str) {

		if (str.equals("all")) {
			return ok(views.html.profile.ads._ad.render(Ad.find.where()
					.eq("contactInfo.email", session("connected"))
					.order("publishedDate desc").findList()));
		}
		return ok(views.html.profile.ads._ad.render(Ad.find.where()
				.eq("status", str)
				.eq("contactInfo.email", session("connected"))
				.order("publishedDate desc").findList()));
	}

	public static Result preLong(Long id) {
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
		Double myAmount = u.profile.myMonney;
		Double cost = Setting.find.where().eq("name", "prelong").findUnique().price;
		if (myAmount >= cost) {
			Ad ad = Ad.find.byId(id);
			ad.expirationDate = new Date(new Date().getTime() + 1000 * 60 * 60
					* 24 * 7);
			ad.update();
			Payment p = new Payment();
			p.paymentType = "substract";
			p.amount = (-1) * cost;

			u.payments.add(p);
			u.profile.myMonney -= cost;
			u.update();
			return ok("fine");
		}
		return ok("notEnough");

	}

	public static Result highlight(Long id) {
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
		Double myAmount = u.profile.myMonney;
		Double cost = Setting.find.where().eq("name", "highlight").findUnique().price;
		if (myAmount >= cost) {
			Ad ad = Ad.find.byId(id);
			AdSetting set = new AdSetting();
			set.name = "highlight";
			ad.settings.add(set);
			ad.update();
			Payment p = new Payment();
			p.paymentType = "substract";
			p.amount = (-1) * cost;

			u.payments.add(p);
			u.profile.myMonney -= cost;
			u.update();
			return ok("fine");
		}
		return ok("notEnough");

	}

	public static Result archive(Long id) {
		Ad ad = Ad.find.byId(id);
		ad.status = "archived";
		ad.update();
		return ok(myAds.render(Ad.find.where()
				.eq("contactInfo.email", session("connected"))
				.order("publishedDate desc").findList()));

	}
	
	public static Result remove(Long id) {
		String a = "delete from authorised_user_ad where ad_id="+id; 
		SqlUpdate  update = Ebean.createSqlUpdate(a);
		Ebean.execute(update);
		Ad ad = Ad.find.byId(id);
		ad.delete();
		
		return ok(myAds.render(Ad.find.where()
				.eq("contactInfo.email", session("connected"))
				.order("publishedDate desc").findList()));

	}

	public static Result autoPreLong(Long id, String str) {
		AdSetting set = AdSetting.find.where().eq("name", "autoprelong")
				.eq("ad.id", id).findUnique();

		set.status = str;
		set.update();
		return ok();

	}

	public static Result update(Long id) {

		DynamicForm requestData = form().bindFromRequest();

		Ad ad = Ad.find.byId(id);
		ad.animal = Animal.find.byId(Long.parseLong(requestData.get("animal")));
		if (ad.animal.id != 5) {
			ad.breed = Breed.find
					.byId(Long.parseLong(requestData.get("breed")));
		}
		ad.birthDate = Integer.parseInt(requestData.get("age"));

		ad.contactInfo.city = City.find.byId(Long.parseLong(requestData
				.get("city")));
		ad.contactInfo.region = Region.find.byId(Long.parseLong(requestData
				.get("region")));
		ad.contactInfo.phone = ContactInfo.phoneCorrect(requestData
				.get("phone"));
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

		AdSetting set = AdSetting.find.where().eq("name", "comment")
				.eq("ad", ad).findUnique();
		set.status = requestData.get("coment_display");
		set.update();

		ad.priceType.currency = "";
		if (requestData.get("payment_type").equals("normal")) {

			ad.priceType.currency = requestData.get("currency");
			ad.priceType.price = Integer.parseInt(requestData.get("money"));
		} else if (requestData.get("payment_type").equals("change")) {
			ad.priceType.price = -2;
		} else {
			ad.priceType.price = -1;
		}

		for (int i = 0; i < ad.tags.size(); i++) {

			ad.tags.get(i).delete();
		}
		ad.tags.add(new Tag(ad.animal.name));
		if (ad.animal.id != 5) {
			ad.tags.add(new Tag(ad.breed.name));
		}
		ad.tags.add(new Tag(ad.title));
		ad.tags.add(new Tag(ad.birthDate.toString()));
		ad.tags.add(new Tag(ad.gender));
		ad.tags.add(new Tag(ad.quantity));
		ad.tags.add(new Tag(ad.contactInfo.region.name));
		ad.tags.add(new Tag(ad.contactInfo.city.name));

		ad.status = "pending";

		ad.update();
		String[] order = requestData.get("image_names").split("&");

		for (int i = 0; i < ad.images.size(); i++) {

			ad.images.get(i).delete();
		}

		for (int i = 0; i < order.length; i++) {

			if (!order[i].equals("") && order[i] != null) {

				AdImage img = new AdImage();
				img.ad = ad;
				img.name = order[i];
				img.position = i + 1;
				img.content = requestData.get(order[i]);
				img.save();
			}
		}

		// saveImages(ad,body.getFiles(),requestData.get("image_order"));

		return ok();
	}

	public static Result getBreeds(Long id) {
		return ok(_breed.render(Animal.find.byId(id)));
	}

	public static Result getCities(Long id) {
		return ok(_city.render(Region.find.byId(id)));
	}

	public static Result show() {
		if (session("connected") != null) {
			return ok(createAd.render(AuthorisedUser
					.findByEmail(session("connected"))));
		}
		return ok(createAd.render(null));
	}

	public static Result edit(Long id) {
		return ok(editAd.render(Ad.find.byId(id)));
	}

	// public static Result search() {
	// return ok(adSearch.render(Ad.find.all(),Animal.find.byId(2L)));
	// }

	public static Result quick_search() {

		DynamicForm requestData = form().bindFromRequest();

		List<Ad> l;
		if (requestData.get("animal").equals("ВО ВСЕХ РАЗДЕЛАХ")) {
			if (requestData.get("str").equals("")
					|| requestData.get("str") == null) {
				l = Ad.find.where().eq("status", "active").findList();
			} else {
				l = Ad.find.where().eq("status", "active")
						.in("tags.name", requestData.get("str")).findList();
			}
			Animal animal = new Animal();
			animal.id=0L;
			animal.name="all";
			return ok(adSearch.render(l, animal));
		}

		else {
			if (requestData.get("str").equals("")
					|| requestData.get("str") == null) {
				l = Ad.find
						.where()
						.eq("status", "active")
						.eq("animal",
								Animal.find.where()
										.eq("name", requestData.get("animal"))
										.findUnique()).findList();
			} else {
				l = Ad.find
						.where()
						.eq("status", "active")
						.eq("animal",
								Animal.find.where()
										.eq("name", requestData.get("animal"))
										.findUnique())
						.in("tags.name", requestData.get("str")).findList();
			}

			Animal animal = Animal.find.where()
					.eq("name", requestData.get("animal")).findUnique();
			return ok(adSearch.render(l, animal));
		}

	}

	public static Result allAd() {
		List<Ad> l = Ad.find.where().eq("status", "active").findList();
		
		Animal animal = new Animal();
		animal.id = 0L;
		animal.name = "all_animal"; 
		return ok(adSearch.render(l, animal));
	}

	public static Result horse() {
		List<Ad> l = Ad.find
				.where()
				.eq("status", "active")
				.eq("animal",
						Animal.find.where().eq("name", "Лошадь").findUnique())
				.findList();
		Animal animal = Animal.find.where().eq("name", "Лошадь").findUnique();
		return ok(adSearch.render(l, animal));
	}

	public static Result camel() {
		List<Ad> l = Ad.find
				.where()
				.eq("status", "active")
				.eq("animal",
						Animal.find.where().eq("name", "Верблюд").findUnique())
				.findList();
		Animal animal = Animal.find.where().eq("name", "Верблюд").findUnique();
		return ok(adSearch.render(l, animal));
	}

	public static Result cow() {
		List<Ad> l = Ad.find
				.where()
				.eq("status", "active")
				.eq("animal",
						Animal.find.where().eq("name", "Корова").findUnique())
				.findList();
		Animal animal = Animal.find.where().eq("name", "Корова").findUnique();
		return ok(adSearch.render(l, animal));
	}

	public static Result cam() {
		List<Ad> l = Ad.find
				.where()
				.eq("status", "active")
				.eq("animal",
						Animal.find.where().eq("name", "Овцы/Козы")
								.findUnique()).findList();
		Animal animal = Animal.find.where().eq("name", "Овцы/Козы")
				.findUnique();
		return ok(adSearch.render(l, animal));
	}

	public static Result other() {
		List<Ad> l = Ad.find
				.where()
				.eq("status", "active")
				.eq("animal",
						Animal.find.where().eq("name", "Другие").findUnique())
				.findList();
		Animal animal = Animal.find.where().eq("name", "Другие").findUnique();
		return ok(adSearch.render(l, animal));
	}

	public static Result searchByType(Long id) {
		return ok(adSearch.render(
				Ad.find.where().eq("status", "active")
						.eq("animal", Animal.find.byId(id)).findList(),
				Animal.find.byId(id)));
	}

	public static Result favorite() {

		if (session("connected") != null) {

			return ok(adSearch.render(
					AuthorisedUser.findByEmail(session("connected")).favorites,
					Animal.find.byId(1L)));

		} else {

			if (request().cookie("userAdsKora") == null)
				return ok(adSearch.render(null, Animal.find.byId(1L)));

			String[] ads = request().cookie("userAdsKora").value().split("_");
			List<Ad> list = new ArrayList<Ad>();
			for (String ad : ads) {
				if (!ad.equals("") && ad != null)
					list.add(Ad.find.byId(Long.parseLong(ad)));

			}
			return ok(adSearch.render(list, Animal.find.byId(1L)));
		}
	}

	public static Result favoriteSize() {
		if (session("connected") != null) {

			return ok(""
					+ AuthorisedUser.findByEmail(session("connected")).favorites
							.size());

		} else {

			if (request().cookie("userAdsKora") == null)
				return ok("");

			String[] ads = request().cookie("userAdsKora").value().split("_");
			List<Ad> list = new ArrayList<Ad>();
			for (String ad : ads) {
				if (!ad.equals("") && ad != null)
					list.add(Ad.find.byId(Long.parseLong(ad)));

			}
			return ok("" + list.size());
		}
	}

	public static int favoriteSizeInt() {
		if (session("connected") != null) {

			return AuthorisedUser.findByEmail(session("connected")).favorites
					.size();

		} else {

			if (request().cookie("userAdsKora") == null)
				return 0;

			String[] ads = request().cookie("userAdsKora").value().split("_");
			List<Ad> list = new ArrayList<Ad>();
			for (String ad : ads) {
				if (!ad.equals("") && ad != null)
					list.add(Ad.find.byId(Long.parseLong(ad)));

			}
			return list.size();
		}
	}

	public static Result sendPrivateMessage(Long ad_id, Long author_id) {
		DynamicForm requestData = form().bindFromRequest();

		AuthorisedUser user = AuthorisedUser.find.where()
				.eq("email", Ad.find.byId(ad_id).contactInfo.email)
				.findUnique();
		if (user != null) {
			String kaptchaExpected = session(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			String kaptchaReceived = requestData.get("kaptcha");

			if (kaptchaExpected.equalsIgnoreCase(kaptchaReceived)) {

				PrivateMessage message = new PrivateMessage();
				message.ad = Ad.find.byId(ad_id);
				message.author = AuthorisedUser.find.byId(author_id);
				message.message = requestData.get("message");
				message.recipent = user;
				message.status = "unread";
				message.title = Ad.find.byId(ad_id).title;
				message.save();
			} else {

				return ok("error");
			}
		} else {

			String message = "Заголовок: " + Ad.find.byId(ad_id).title + "::";
			message += requestData.get("message") + "::";

			message += "от " + AuthorisedUser.find.byId(author_id).userName
					+ " <" + AuthorisedUser.find.byId(author_id).email + ">";
			Emailing.send("Қора.kz", new String[] { " <"
					+ Ad.find.byId(ad_id).contactInfo.email + ">" },
					private_message.render(message, "not_registred").body());
			return ok("not_registred");
		}
		return ok();
	}

	public static Result replayPrivateMessage(Long author_id, Long ad_id,
			Long recipent_id, String m) {

		PrivateMessage message = new PrivateMessage();
		message.ad = Ad.find.byId(ad_id);
		message.author = AuthorisedUser.find.byId(author_id);
		message.message = m;
		message.recipent = AuthorisedUser.find.byId(recipent_id);
		message.status = "unread";
		message.title = "(re)" + Ad.find.byId(ad_id).title;
		message.save();

		return ok();
	}

	public static Result deleteMessages(String ids, String type) {

		for (String id : ids.split("_")) {
			if (!id.equals("") && id != null) {
				PrivateMessage message = PrivateMessage.find.byId(Long
						.parseLong(id));

				if (type.equals("send")) {
					if (!message.status.equals("recipent-deleted")) {
						message.status = "author-deleted";
						message.update();
					} else
						message.delete();
				} else {
					if (!message.status.equals("author-deleted")) {
						message.status = "recipent-deleted";
						message.update();
					} else
						message.delete();
				}
			}

		}
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
		if (type.equals("send"))
			return ok(views.html.profile.messages._message
					.render(PrivateMessage.find.where().eq("author_id", u.id)
							.ne("status", "author-deleted")
							.order("send_date desc").findList()));

		return ok(views.html.profile.messages._message
				.render(PrivateMessage.find.where().eq("recipent_id", u.id)
						.ne("status", "recipent-deleted")
						.order("send_date desc").findList()));
	}

	public static Result readAsMessages(String ids) {

		for (String id : ids.split("_")) {
			if (!id.equals("") && id != null) {
				PrivateMessage message = PrivateMessage.find.byId(Long
						.parseLong(id));
				message.status = "readed";
				message.update();
			}
		}
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
		return ok(views.html.profile.messages._message
				.render(PrivateMessage.find.where().eq("recipent_id", u.id)
						.ne("status", "recipent-deleted")
						.order("send_date desc").findList()));
	}

	public static Result addFavorite(Long id, String type) {

		if (session("connected") != null) {
			AuthorisedUser user = AuthorisedUser
					.findByEmail(session("connected"));

			if (type.equals("add")) {
				if (user.favorites == null) {
					user.favorites = new ArrayList<Ad>();
				}
				user.favorites.add(Ad.find.byId(id));
			} else {

				user.favorites.remove(Ad.find.byId(id));
			}
			user.update();
		} else {
			String ids = "";
			if (request().cookie("userAdsKora") != null) {
				ids = request().cookie("userAdsKora").value();
			}
			if (type.equals("add")) {
				ids += id + "_";
			} else {
				String b = "";
				for (String s : ids.split("_")) {
					if (!s.equals(id.toString())) {
						b += s + "_";
					}
				}
				ids = b;
			}
			response().discardCookie("userAdsKora");
			response().setCookie("userAdsKora", ids, (86400 * 7));

		}

		return ok();
	}

	public static boolean checkFavorite(Long id) {

		if (session("connected") != null) {

			AuthorisedUser user = AuthorisedUser
					.findByEmail(session("connected"));
			if (user.favorites.contains(Ad.find.byId(id))) {
				return true;
			}
			return false;
		} else {

			if (request().cookie("userAdsKora") == null)
				return false;

			String[] ads = request().cookie("userAdsKora").value().split("_");

			for (String ad : ads) {
				if (ad.equals(id.toString()))
					return true;

			}
			return false;
		}

	}

	public static Result comment(Long id, Long com_id) {

		DynamicForm requestData = form().bindFromRequest();
		String kaptchaExpected = session(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String kaptchaReceived = requestData.get("kaptcha");

		if (kaptchaExpected.equalsIgnoreCase(kaptchaReceived)) {

			Comment comment = new Comment();
			comment.ad = Ad.find.byId(id);
			comment.email = requestData.get("comment_email");
			comment.text = requestData.get("comment_message");
			comment.name = requestData.get("comment_author");
			if (com_id != 0) {
				comment.coment = Comment.find.byId(com_id);

			}
			comment.save();

			return ok(_comment.render(Ad.find.byId(id).comments));
		} else {
			return ok("kapcha_error");
		}
	}

	public static Result searchAd(Integer page, String sort) {
		DynamicForm requestData = form().bindFromRequest();
		String anim = ""; 
		Double costStartTg = 0.0;
		Double costEndTg = 99999999.9;
		Double costStartD = 0.0;
		Double costEndD = 99999999.9;
		String b = "", loc = "", r = "", tag = "", gender = "", quantity = "", pic = "";
		int endYear = 20000, startYear = 0, f = -1, l = -1;

		if(!requestData.get("animal").equals("0"))
			anim = "inner join animal ani on a.animal_id=ani.id and ani.id="
					+requestData.get("animal");
		
		if (!requestData.get("breed").equals("all"))
			b = "inner join breed b on a.breed_id=b.id and b.id="
					+ requestData.get("breed");
		if (!requestData.get("region").equals("all"))
			r = "inner join region r on r.id = c.region_id and r.id="
					+ requestData.get("region");
		if (!requestData.get("searchText").equals(""))
			tag = "inner join tag t on a.id=t.ad_id and t.name='"
					+ requestData.get("searchText") + "'";
		if (!requestData.get("gender").equals(""))
			gender = "and a.gender='" + requestData.get("gender") + "'";
		if (requestData.get("quantity") != null)
			quantity = " and a.quantity ='" + requestData.get("quantity") + "'";
		if (!requestData.get("ageStart").equals(""))
			startYear = Integer.parseInt(requestData.get("ageStart"));
		if (!requestData.get("ageEnd").equals(""))
			endYear = Integer.parseInt(requestData.get("ageEnd"));
		if (requestData.get("map") != null)
			loc = "and c.location_id is not null";
		if (requestData.get("picture") != null)
			pic = "and a.id in (select ad_id from ad_image)";
		if (requestData.get("change") != null)
			f = -2;
		if (requestData.get("negotiable_price") != null)
			l = 0;

		Double usdK = session("USDtoKZT") != null ? Double
				.parseDouble(session("USDtoKZT")) : 180.0;
		Double kztU = session("KZTtoUSD") != null ? Double
				.parseDouble(session("KZTtoUSD")) : 0.0055244;

		if (requestData.get("currency").equals("USD")) {

			if (!requestData.get("costStart").equals("")) {
				costStartD = Double.parseDouble(requestData.get("costStart"));
				costStartTg = usdK * costStartD;

			}
			if (!requestData.get("costEnd").equals("")) {
				costEndD = Double.parseDouble(requestData.get("costEnd"));
				costEndTg = usdK * costEndD;
			}
		} else {

			if (!requestData.get("costStart").equals("")) {
				costStartTg = Double.parseDouble(requestData.get("costStart"));
				costStartD = kztU * costStartTg;
			}
			if (!requestData.get("costEnd").equals("")) {
				costEndTg = Double.parseDouble(requestData.get("costEnd"));
				costEndD = kztU * costEndTg;
			}
		}

		if (sort.equals("price")) {

			sort = "p.price * CASE WHEN p.currency='USD' THEN " + usdK
					+ " ELSE 1 END";
		} else if (sort.equals("price_desc")) {
			sort = "p.price * CASE WHEN p.currency='USD' THEN " + usdK
					+ " ELSE 1 END desc";
		}

		String a = "SELECT a.id from ad a \n"
				+ " "
				+ anim
				+ b
				+ " inner join contact c on a.contact_info_id = c.id "
				+ loc
				+ r
				+ "\n"
				+ tag
				+ " inner join price p on p.id=a.price_type_id and ((p.price>="
				+ f
				+ " and p.price<"
				+ l
				+ ") or  ((p.price>="
				+ costStartD
				+ " and p.currency='USD' and p.price<="
				+ costEndD
				+ ") or  (p.price>="
				+ costStartTg
				+ " and p.currency='KZT' and p.price<="
				+ costEndTg
				+ "))) "
				+ " where a.status='active' "
				+ gender
				+ quantity
				+ " and (a.birth_date between "
				+ startYear
				+ " and "
				+ endYear
				+ ") "
				+ pic
				+ " order by "
				+ sort
				+ " \n"
				+ " limit 30 offset " + (page * 30);

		String a2 = "SELECT count(*) from ad a \n"
				+ " "
				+ anim
				+ b
				+ " inner join contact c on a.contact_info_id = c.id "
				+ loc
				+ r
				+ "\n"
				+ tag
				+ " inner join price p on p.id=a.price_type_id and ((p.price>="
				+ f
				+ " and p.price<"
				+ l
				+ ") or  ((p.price>="
				+ costStartD
				+ " and p.currency='USD' and p.price<="
				+ costEndD
				+ ") or  (p.price>="
				+ costStartTg
				+ " and p.currency='KZT' and p.price<="
				+ costEndTg
				+ "))) "
				+ " where a.status='active' "
				+ gender
				+ quantity
				+ " and (a.birth_date between "
				+ startYear
				+ " and "
				+ endYear
				+ ") " + pic;

		
		System.out.println(a2);

		SqlQuery sqlQuery2 = Ebean.createSqlQuery(a);

		SqlQuery sqlQuery3 = Ebean.createSqlQuery(a2);

		List<SqlRow> list2 = sqlQuery2.findList();
		List<SqlRow> list3 = sqlQuery3.findList();

		List<Ad> ads = new ArrayList<Ad>();

		for (int i = 0; i < list2.size(); i++) {
			long id = list2.get(i).getLong("id");
			ads.add(Ad.find.byId(id));
		}

		System.out.println(list3.size());
		System.out.println(list3.get(0));
		Ad tmp = new Ad();
		tmp.id = 0L;
		tmp.quantity = list3.get(0).getInteger("count").toString();
		ads.add(tmp);
		return ok(_ad_list.render(ads));
	}

}
