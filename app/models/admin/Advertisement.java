package models.admin;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "advertisements")
public class Advertisement extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Advertisement> find = new Model.Finder<Long, Advertisement>(
			Long.class, Advertisement.class);

	@Id
	public Long id;

	public Integer position = 0;

	public String company;

	public String placeOnPage;

	// @Lob
	public byte[] file;

	public String fileType;

	public String status = "show";

	public Date publishDate = new Date();

	public Date updateDate = new Date();

	public Date tillToDate;

	public static void addPosition() {
		List<Advertisement> advertisements = find.all();
		for (Advertisement temp : advertisements) {
			++temp.position;
			temp.update();
		}
	}

	public static void removePosition(Advertisement advertisement) {
		List<Advertisement> advertisements = find.where()
				.gt("position", advertisement.position).findList();
		for (Advertisement temp : advertisements) {
			--temp.position;
			temp.update();
		}
	}

	public static void rePosition(Advertisement advertisement, String direction) {
		if (direction.equals("up")) {
			--advertisement.position;
			Advertisement temp = find.where()
					.eq("position", advertisement.position).findUnique();
			++temp.position;
			temp.update();
			advertisement.update();
		} else if (direction.equals("down")) {
			++advertisement.position;
			Advertisement temp = find.where()
					.eq("position", advertisement.position).findUnique();
			--temp.position;
			temp.update();
			advertisement.update();
		}
	}

	
	public static List<Advertisement> findAdvetisements(String position){
		Date today =  new Date();
		return find.where().eq("placeOnPage",position).le("updateDate", today).ge("tillToDate", today).orderBy("position").findList();
	}
	
	public static int findAdvetisementsCount(String position){
		Date today =  new Date();
		return find.where().eq("placeOnPage",position).le("updateDate", today).ge("tillToDate", today).orderBy("position").findRowCount();
	}
}
