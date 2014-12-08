package models;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public class Emailing {

	public static void send(String subject, String recipients[], String body) {
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class)
				.email();
		mail.setSubject(subject);
		mail.setRecipient(recipients);
		mail.setFrom("Қора <noreply@kora.kz>");
		// sends html
		new Thread() {
			public void run() {
				mail.sendHtml(body);
			}
		}.start();
		// sends text/text
		// mail.send( "text" );
		// sends both text and html
		// mail.send( "text", "<html>html</html>");
	}

	public static void send(String subject, String recipient, String body) {
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class)
				.email();
		mail.setSubject(subject);
		mail.setRecipient(recipient);
		mail.setFrom("Қора <noreply@kora.kz>");
		// sends html
		new Thread() {
			public void run() {
				mail.sendHtml(body);
			}
		}.start();

		// sends text/text
		// mail.send( "text" );
		// sends both text and html
		// mail.send( "text", "<html>html</html>");
	}
}
