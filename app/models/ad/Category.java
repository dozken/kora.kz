package models.ad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "categories")
public class Category extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Category> find = new Model.Finder<Long, Category>(
			Long.class, Category.class);
	@Id
	public Long id;

	@ManyToOne
	public Section section;

	@ManyToOne
	public Category parentCategory;

	@OneToMany(mappedBy = "parentCategory")
	public List<Category> subCategories;

	public String name;

	@OneToMany
	public List<Ad> ads;

	public List<Category> findSubCategories() {
		return find.where().eq("parentCategory", this).orderBy("id")
				.findList();
	}

	public boolean isParent() {
		return this.subCategories != null && this.subCategories.size() > 0 ? true
				: false;
	}
}
