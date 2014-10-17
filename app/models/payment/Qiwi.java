package models.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class Qiwi {
	@Id
	@Column(unique=true)
	public String txn_id;
	
	@Column(unique=true,nullable=true)
	public String prv_txn;
	
	public String result;
	
	public String comment;
	
	
	public Double sum;

	public static Integer check() {
		// TODO Auto-generated method stub
		if(true)
			return 0;
		else
			return 1;
	}

	public static Integer pay() {
		// TODO Auto-generated method stub
		if(true)
			return 0;
		else
			return 1;
	}
}
