package controllers;

import static play.data.Form.form;
import models.payment.Qiwi;
import play.data.DynamicForm;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.xml.payment.*;

public class Payments extends Controller {

	/**
	 * <?xml version="1.0" encoding="UTF-8"?> <response>
	 * <osmp_txn_id></osmp_txn_id> <prv_txn></prv_txn> <sum></sum>
	 * <result></result> <comment></comment> </response>
	 * 
	 * @return
	 */
	@BodyParser.Of(play.mvc.BodyParser.Xml.class)
	public static Result qiwi() {
		// TODO
		// 92.46.53.228
		// test 212.154.215.82
		// CHECK FOR QIWI IP
		String remoteIP = request().remoteAddress();
		if (remoteIP.equals("92.46.53.228")
				|| remoteIP.equals("212.154.215.82")) {
			DynamicForm requestData = form().bindFromRequest();
			if (requestData == null) {
				return badRequest("");
			} else {
				if (requestData.get("command").equals("check")) {
					Integer txn_id = Integer
							.parseInt(requestData.get("txn_id"));
					String account = requestData.get("account");
					if (remoteIP.equals("212.154.215.82")) {
						return ok(check.render(txn_id, 0, ""));
					} else {
						Integer result = Qiwi.check(remoteIP, txn_id, account);
						return ok(check.render(txn_id, result, ""));
					}
				} else if (requestData.get("command").equals("pay")) {

					Integer txn_id = Integer
							.parseInt(requestData.get("txn_id"));
					String txn_date = requestData.get("txn_date");
					String account = requestData.get("account");
					Double sum = Double.parseDouble(requestData.get("sum"));
					if (remoteIP.equals("212.154.215.82")) {
						return ok(pay.render(txn_id,
								(int) (Math.random() * 101), sum, 0, ""));
					} else {
						Integer prv_txn = Qiwi.pay(remoteIP, txn_id, txn_date,
								account, sum);
						Integer result = prv_txn == -1 ? 1 : 0;
					}
				}
			}
		} else {
			return ok(views.html.notFoundPage.render(""));

		}
		return ok(views.html.notFoundPage.render(""));
	}
}
