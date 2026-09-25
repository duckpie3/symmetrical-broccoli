package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_image")
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("product_image_id")
	@Column(name = "product_image_id")
	private Integer productImageId;

	@JsonProperty("product_id")
	@Column(name = "product_id")
	private Integer productId;
	
	@JsonProperty("image")
	@Column(name = "image")
	private String image;
	
	@JsonProperty("status")
	@Column(name = "status")
	private Integer status;

	public Integer getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(Integer productImageId) {
		this.productImageId = productImageId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
