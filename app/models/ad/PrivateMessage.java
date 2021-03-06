package models.ad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.user.AuthorisedUser;
import play.db.ebean.Model;

@Entity
@Table(name = "ad_messages")
public class PrivateMessage extends Model {

	/**
	 *
	 */
	public static final Finder<Long, PrivateMessage> find = new Finder<Long, PrivateMessage>(
			Long.class, PrivateMessage.class);
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@OneToOne
	public AuthorisedUser author;

	@OneToOne
	public AuthorisedUser recipent;

	@ManyToOne
	public Ad ad;

	public String title;

	@Column(columnDefinition = "TEXT")
	public String message;

	public Date sendDate = new Date();

	public String status = "unread";
}
