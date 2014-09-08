package models;
import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public class Emailing {

	public static void send(String subject, String recipients[],String body){
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		mail.setSubject(subject);
		mail.setRecipient(recipients);
		mail.setFrom("Қора <noreply@kora.kz>");
		//sends html
		mail.sendHtml(body);
		//sends text/text
		//mail.send( "text" );
		//sends both text and html
		//mail.send( "text", "<html>html</html>");
	}
}