package com.product.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
	@Id
	private int category_id;
	private String category;
	private String tag;
	private int status;

	public void setCategory_id(int category_id) {
		if (category_id < 0) {
			throw new IllegalArgumentException("id no puede ser nagativo.");
		}
		this.category_id = category_id;
	}

	public void setCategory(String category) {
		if (category.equals("")) {
			throw new IllegalArgumentException("nombre de categoria no puede estar vacio");
		}
		this.category = category;
	}

	public void setTag(String tag) {
		if (tag.equals("")) {
			throw new IllegalArgumentException("tag no puede estar vacio");
		}
		this.tag = tag;
	}

	public void setStatus(int status) {
		if (status != 0 && status != 1) {
			throw new IllegalArgumentException("status debe ser 1 o 0");
		}
		this.status = status;
	}

	public int getCategory_id() {
		return category_id;
	}

	public String getCategory() {
		return category;
	}

	public String getTag() {
		return tag;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return String.format("{%d, %s, %s, %d}", category_id, category, tag, status);
	}

	public Category() {
		super();
	}

	public Category(Integer category_id, String category, String tag, Integer status) {
		setCategory_id(category_id);
		setCategory(category);
		setTag(tag);
		setStatus(status);
	}
}