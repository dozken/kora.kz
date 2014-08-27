package controllers;

import models.ad.Animal;
import models.ad.Breed;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.*;
import views.html.admin.filters.breed.*;
public class Filters extends Controller {

	public static Result getBreeds(Long id){
		return ok(_breed.render(models.ad.Animal.find.byId(id)));
	}
	
	public static Result addBreed(){
		Form<Breed> breedForm = form(Breed.class).bindFromRequest();
		flash().remove("error");
		if(breedForm.hasErrors()){
			flash("error","Не могу сохранить!");
			return badRequest();
		}else{
			Breed breed = breedForm.get();
			breed.save();
			
			return ok(_breed.render(breed.animal));
		}
		
	}
	
	public static Result updateBreed(Long id,String name){
		if(Breed.find.where().eq("name",name).findRowCount()>0){
			flash("error","Не могу обновить, уже есть порода с именем <strong>"+name+"</strong>!");
			return badRequest();
		}else{
			Breed breed = Breed.find.byId(id);
			breed.name = name;
			breed.update();			
			return ok(_breed.render(breed.animal));
		}
	}
	
	public static Result deleteBreed(Long id){
		Breed breed = Breed.find.byId(id);
		Animal animal = breed.animal;
		if(breed.ads!=null&&breed.ads.size()>0){
			flash("error","Нельзя удалить, <strong>"+breed.ads.size()+"</strong> объявления с породой <strong>"+breed.name+"</strong>!");
			//return badRequest(_breed.render(animal));
			return ok(_breed.render(animal));
		}
		else{
			breed.delete();
			return ok(_breed.render(animal));
		}
		
	}
	
	public static Result addRegion(){
		return TODO;
	}
	
	public static Result updateRegion(Long id){
		return TODO;
	}
	
	public static Result deleteRegion(Long id){
//		Breed
		return TODO;
	}
	
	public static Result addCity(){
		return TODO;
	}
	
	public static Result updateCity(Long id){
		return TODO;
	}
	
	public static Result deleteCity(Long id){
//		Breed
		return TODO;
	}	
	
}
