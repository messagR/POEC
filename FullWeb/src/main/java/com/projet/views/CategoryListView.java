package com.projet.views;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.projet.donnees.Category;

@ManagedBean(name="categoryListView")
@RequestScoped
public class CategoryListView {

	@ManagedProperty("#{categoriesBean}")
	private Categories categoriesBean;

	public List<Category> getCategories(){
		return this.categoriesBean.getCategories();
	}

	public void setCategoriesBean(Categories categories) {
		this.categoriesBean = categories;
	}

	public Categories getCategoriesBean() {
		return this.categoriesBean;
	}

}
