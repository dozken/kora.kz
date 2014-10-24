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
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <response>
	 * <osmp_txn_id></osmp_txn_id>
	 * <prv_txn></prv_txn>
	 * <sum></sum>
	 * <result></result>
	 * <comment></comment>
	 * </response>
	 * @return
	 */
	@BodyParser.Of(play.mvc.BodyParser.Xml.class)
	public static Result qiwi() {
		//TODO
		//92.46.53.228
		//test 212.154.215.82
		//CHECK FOR QIWI IP
		String remote = request().remoteAddress();
		System.out.println("IP:"+remote);
		DynamicForm requestData = form().bindFromRequest();
		if(requestData==null){
			return badRequest("");
		} else {
			if(requestData.get("command").equals("check")){
				Integer txn_id = Integer.parseInt(requestData.get("txn_id"));
				String account = requestData.get("account");
				
				Integer result = Qiwi.check(txn_id,account);
				return ok(check.render(txn_id,result,""));
			} else if(requestData.get("command").equals("pay")){
				Integer txn_id = Integer.parseInt(requestData.get("txn_id"));
				String txn_date = requestData.get("txn_date");
				String account = requestData.get("account");
				Double sum = Double.parseDouble(requestData.get("sum"));
				Integer prv_txn = Qiwi.pay(txn_id,txn_date,account,sum);
				//@(osmp_txn_id :Integer, prv_txn :Integer, sum :Double, result :Integer, comment:String)
				System.out.println("ashato "+prv_txn + "--"+(prv_txn==1 ? 1 : 0));
				Integer result = prv_txn==-1 ? 1 : 0;
				return ok(pay.render(txn_id,prv_txn,sum,result,""));
			}
			
		}
		/*
		Document dom = request().body().asXml();
		if (dom == null) {
			return badRequest("Expecting Xml data");
		} else {
			String name = XPath.selectText("//name", dom);
			if (name == null) {
				return badRequest("<message \"status\"=\"KO\">Missing parameter [name]</message>");
			} else {
				return ok("<message \"status\"=\"OK\">Hello " + name
						+ "</message>");
			}
		}
		*/
		return ok("");
	}
}
