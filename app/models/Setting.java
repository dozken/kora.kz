package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.ad.Animal;
import play.db.ebean.Model;

@Entity
public class Setting extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Model.Finder<Long, Setting> find = new Model.Finder<Long, Setting>(
			Long.class, Setting.class);
	
	@Id
	public Long id;
	
	public Integer position;
	
	public String name;
	
	public String category;
	
	public static List<Setting> findByCategory(String category){
		return find.where().eq("category", category).orderBy("position").findList();		
	}
}
