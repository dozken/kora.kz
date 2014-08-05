package models.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Payment extends Model {

	@Id
	public Long id;
	
	public Date paymentDate = new Date();
	
	public Double amount = 0.0;
	
	public String paymentType;
}
