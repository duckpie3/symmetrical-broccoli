package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("category_id")
	@Column(name = "category_id")
	private Integer category_id;
	@JsonProperty("category")
	@Column(name = "category")
	private String category;
	@JsonProperty("tag")
	@Column(name = "tag")
	private String tag;
	@JsonProperty("status")
	@Column(name = "status")
	private Integer status;

	public void setCategory_id(Integer category_id) {
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

	public void setStatus(Integer status) {
		if (status != 0 && status != 1) {
			throw new IllegalArgumentException("status debe ser 1 o 0");
		}
		this.status = status;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public String getCategory() {
		return category;
	}

	public String getTag() {
		return tag;
	}

	public Integer getStatus() {
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