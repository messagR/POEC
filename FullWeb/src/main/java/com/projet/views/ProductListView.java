package com.projet.views;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.projet.donnees.Product;

@ManagedBean(name="productListView")
@RequestScoped
public class ProductListView {

	@ManagedProperty("#{categoriesBean}")
	private Categories categoriesBean;

	@ManagedProperty("#{param.categoryName}")
	private String categoryName;

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Product> getProducts() {
		return this.categoriesBean.getCategories().stream()
				.filter(c -> c.getName().equals(this.categoryName))
				.findFirst().get().getProducts();
	}

	public void setCategoriesBean(Categories categoriesBean) {
		this.categoriesBean = categoriesBean;
	}

}
