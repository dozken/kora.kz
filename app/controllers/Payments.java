package controllers;

import static play.data.Form.form;
import play.data.DynamicForm;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.xml.payment.qiwi;

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
		DynamicForm requestData = form().bindFromRequest();
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
		return ok(qiwi.render(requestData));
	}
}
