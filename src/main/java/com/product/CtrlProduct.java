package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
public class CtrlProduct {
	
	@GetMapping
	public List<Category> getCategories(){
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category(1, "Lentes", "Lts", 1));
		Category relojes = new Category(2, "Relojes", "Rljs", 1);
		categories.add(relojes);
		Category calcetines = new Category(3, "Calcetines", "Clts", 1);
		categories.add(calcetines);
		return categories;
	}
	
}
