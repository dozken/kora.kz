package models.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import models.user.AuthorisedUser;
import models.user.Payment;
import models.user.Profile;
import play.db.ebean.Model;

@Entity
public class Qiwi extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(unique = true)
	public Integer txn_id;
	public String txn_date;

	@Column(unique = true, nullable = true)
	public Integer prv_txn;
	
	public String account;
	
	public Double sum;
	
	public String comment;
	
	public static final Model.Finder<Long, Qiwi> find = new Model.Finder<Long, Qiwi>(
			Long.class, Qiwi.class);

	public static Integer check(String remoteIP, Integer txn_id, String account) {
		// TODO Auto-generated method stub

		if (Qiwi.find.where().eq("txn_id", txn_id).findRowCount() == 0
				&& Profile.find.where().eq("phone", account).findRowCount() == 1)
			return 0;
		else
			return 1;
	}

	public static Integer pay(String remoteIP,Integer txn_id, String txn_date, String account,
			Double sum) {
		// TODO Auto-generated method stub
		if (Qiwi.find.where().eq("txn_id", txn_id).findRowCount() == 0
				&& Profile.find.where().eq("phone", account).findRowCount() == 1) {
			
			Qiwi qiwi = new Qiwi();
			qiwi.txn_id = txn_id;
			qiwi.txn_date = txn_date;
			qiwi.prv_txn = Qiwi.find.findRowCount()+1;
			qiwi.account = account;
			qiwi.sum = sum;
			qiwi.save();
			
			AuthorisedUser u = Profile.find.where().eq("phone", account).findUnique().user;
			if(u==null)
				System.out.println("tarakan");
			else
				System.out.println("tarkan:"+u.email);
			Payment p = new Payment();
			p.amount = sum;
			p.paymentType = "add";			
			u.payments.add(p);
	        u.profile.myMonney+=p.amount;
			u.update();
			
			return qiwi.prv_txn;
		} else
			return -1;
	}
}
