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
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@ManyToOne
	public AuthorisedUser authorisedUser;
	
	public Date paymentDate = new Date();
	
	public Double amount = 0.0;
	
	public String paymentType;
}
