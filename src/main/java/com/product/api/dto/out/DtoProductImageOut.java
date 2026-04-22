package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class DtoProductImageOut {
	@JsonProperty("product_image_id")
	@NotNull(message="El product_image_id es obligatorio.")
	private Integer productImageId;
	
	@JsonProperty("image")
	@NotNull(message="La imagen es obligatoria.")
	private String image;

	public Integer getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(Integer productImageId) {
		this.productImageId = productImageId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
