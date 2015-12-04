package com.projet.donnees;

import java.util.ArrayList;
import java.util.List;

public class Category {

	private String name;

	private String description;

	private String image = "chaton.jpg";

	private List<Product> products = new ArrayList<>();

	public Category(){}

	public Category(String name, String description){
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
