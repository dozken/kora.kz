package models.contact;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.Location;
import models.ad.Ad;
import models.user.AuthorisedUser;
import models.user.Profile;
import play.db.ebean.Model;

@Entity
@Table(name="contact")
public class ContactInfo extends Model {

	/**
	 * 
	 */
    public static final Model.Finder<Long, ContactInfo> find = new Model.Finder<Long, ContactInfo>(
            Long.class, ContactInfo.class);
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@OneToOne(mappedBy="contactInfo")
	public Ad ad;
	
	@ManyToOne
	public Region region;
	
	@ManyToOne
	public City city;
	
	public String company;
	
	public String phone;
	
	public String email;

    @OneToOne(cascade=CascadeType.ALL)
    public Location location;
	
	
	public ContactInfo (){
		
	}
	
	public ContactInfo (Region r, String c,String p, String e, String m){
		region = r;
		company = c;
		phone = "";

		email = e;

		save();
		
	}

    public static String phoneCheck(AuthorisedUser user, String phone){

        phone = phone.replaceAll("[-+ )(]","");

        if(user.profile.phone!=null && user.profile.phone.equals(phone)){
            System.out.println("ten emes");
            return phone;
        }else {
            int row = Profile.find.where().eq("phone", phone).findRowCount();

            if (row > 0) {
                System.out.println("kuip ketti");
                return "exists";
            }
        }
        return phone;
    }
    public static String phoneCorrect( String phone){


        return phone.replaceAll("[-+ )(]","");
    }
}
