package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.ad.Ad;
import models.ad.PrivateMessage;
import models.user.AuthorisedUser;
import models.user.Payment;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.ads.myAds;
import views.html.profile.info.myInfo;
import views.html.profile.messages._message;
import views.html.profile.messages._read;
import views.html.profile.messages.myMessages;
import views.html.profile.payments._table;
import views.html.profile.payments.myPayments;
import views.html.profile.settings.mySettings;
import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.avaje.ebean.Expr;

@SubjectPresent
public class Manage extends Controller {

	public static Result myAds() {
		return ok(myAds.render(Ad.find.where()
				.eq("contactInfo.email", session("connected"))
				.order("publishedDate desc").findList()));

	}

	public static Result myMessages() {

		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));

		List<PrivateMessage> mes = PrivateMessage.find.where()
				.eq("recipent_id", u.id).ne("status", "recipent-deleted")
				.order("send_date desc").findList();
		return ok(myMessages.render(mes));
	}

	public static Result getMessageType(String s) {
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));

		if (s.equals("all")) {
			return ok(_message.render(PrivateMessage.find
					.where()
					.or(Expr.eq("recipent_id", u.id),
							Expr.eq("author_id", u.id)).order("send_date desc")
					.findList()));
		}

		if (s.equals("send")) {
			return ok(_message.render(PrivateMessage.find.where()
					.eq("author_id", u.id).ne("status", "author-deleted")
					.order("send_date desc").findList()));
		}

		return ok(_message.render(PrivateMessage.find.where()
				.eq("recipent_id", u.id).ne("status", "recipent-deleted")
				.order("send_date desc").findList()));
	}

	public static Result read(Long id) {
		PrivateMessage message = PrivateMessage.find.byId(id);
		message.status = "readed";
		message.update();
		return ok(_read.render(message));
	}

	public static Result myPayments() {
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));

		return ok(myPayments.render(Payment.find.where().eq("user", u)
				.order("paymentDate desc").findList()));
	}

	public static Result addMoney(String type, Double money) {

		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
		Payment p = new Payment();
		p.amount = money;
		p.paymentType = type;
		u.payments.add(p);
		u.profile.myMonney += money;
		u.update();
		return ok(_table.render(Payment.find.where().eq("user", u)
				.order("paymentDate desc").findList()));

	}

	public static Result paymentReport(String filter, String start, String end) {
		AuthorisedUser u = AuthorisedUser.findByEmail(session("connected"));
		if (start != null && !start.equals("") && end != null
				&& !end.equals("")) {
			try {
				Date s = new SimpleDateFormat("dd/MM/yyyy").parse(start);
				Date e = new SimpleDateFormat("dd/MM/yyyy").parse(end);
				if (filter.equals("all")) {
					return ok(_table.render(Payment.find.where().eq("user", u)
							.between("paymentDate", s, e)
							.order("paymentDate desc").findList()));
				}
				if (filter.equals("add")) {
					return ok(_table.render(Payment.find.where().eq("user", u)
							.eq("paymentType", "add")
							.between("paymentDate", s, e)
							.order("paymentDate desc").findList()));
				}
				return ok(_table.render(Payment.find.where().eq("user", u)
						.eq("paymentType", "substract")
						.between("paymentDate", s, e).order("paymentDate desc")
						.findList()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (filter.equals("all")) {
				return ok(_table.render(Payment.find.where().eq("user", u)
						.order("paymentDate desc").findList()));
			}
			if (filter.equals("add")) {
				return ok(_table.render(Payment.find.where().eq("user", u)
						.eq("paymentType", "add").order("paymentDate desc")
						.findList()));
			}
			return ok(_table.render(Payment.find.where().eq("user", u)
					.eq("paymentType", "substract").order("paymentDate desc")
					.findList()));
		}
		return ok();
	}

	public static Result mySettings() {
		return ok(mySettings.render());
	}

	public static Result myInfo() {
		flash("name", "value");
		return ok(myInfo.render(AuthorisedUser
				.findByEmail(session("connected"))));
	}

	public static Result saveProfile() {

		return ok();
	}

	public static Result editProfile() {

		return ok();
	}

}
