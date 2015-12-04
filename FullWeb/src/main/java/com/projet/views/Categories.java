package com.projet.views;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.projet.donnees.Category;
import com.projet.donnees.Product;

@ManagedBean(name="categoriesBean")
@ApplicationScoped
public class Categories {

	private List<Category> categories;

	public Categories(){
		this.categories = new ArrayList<>();
		Category cat1 = new Category("Category1", "Description1");
		cat1.getProducts().add(new Product("Product11", "description11", 10.50));
		cat1.getProducts().add(new Product("Product12", "description12", 19.99));
		Category cat2 = new Category("Category2", "Description2");
		cat2.getProducts().add(new Product("Product21", "description21", 99.90));
		cat2.getProducts().add(new Product("Product22", "description22", 149.99));
		Category cat3 = new Category("Category3", "Description3");
		Category cat4 = new Category("Category4", "Description4");

		this.categories.add(cat1);
		this.categories.add(cat2);
		this.categories.add(cat3);
		this.categories.add(cat4);
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
