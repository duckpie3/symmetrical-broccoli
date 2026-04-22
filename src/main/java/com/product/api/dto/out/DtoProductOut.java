package com.product.api.dto.out;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.api.entity.Product;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

public class DtoProductOut {
	@Id
	@JsonProperty("product_id")
	private Integer productId;

	@JsonProperty("gtin")
	private String gtin;

	@JsonProperty("product")
	private String product;

	@JsonProperty("description")
	private String description;

	@JsonProperty("price")
	private Float price;

	@JsonProperty("stock")
	private Integer stock;

	@JsonProperty("category")
	private String category;

	@Transient
	private List<String> images;

	
	public DtoProductOut(Product product) {
		this.productId = product.getProductId();
		this.gtin = product.getGtin();
		this.product = product.getProduct();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.stock = product.getStock();
		// this.category = product.getCategory();
		// this.images = product.getImages();
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
}
