package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Section extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Section> find = new Model.Finder<Long, Section>(
			Long.class, Section.class);

	@Id
	public Long id;

	public String name;

	@OneToMany
	public List<Ad> ads;

	@OneToMany
	public List<Category> categorys;

	public List<Category> getCategorys() {
		return Category.find.where().eq("section", this).orderBy("name").findList();
	}
}
