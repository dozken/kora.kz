package models.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Payment extends Model {

	/**
	 * 
	 */
	public static final Model.Finder<Long, Payment> find = new Model.Finder<Long, Payment>(
            Long.class, Payment.class);
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@ManyToOne
	public AuthorisedUser user;
	
	public Date paymentDate = new Date();
	
	public Double amount = 0.0;
	
	public String paymentType;
}
