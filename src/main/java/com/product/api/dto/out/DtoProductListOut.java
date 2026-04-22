package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.api.entity.Product;

public class DtoProductListOut {
	
	@JsonProperty("product_id")
	private Integer productId;
	
	@JsonProperty("gtin")
	private String gtin;

	@JsonProperty("product")
	private String product;

	@JsonProperty("price")
	private Float price;

	@JsonProperty("status")
	private Integer status;

	public DtoProductListOut(Integer productId, String gtin, String product, Float price, Integer status) {
		super();
		this.productId = productId;
		this.gtin = gtin;
		this.product = product;
		this.price = price;
		this.status = status;
	}

	public DtoProductListOut(Product product1) {
		super();
		this.productId = product1.getProductId();
		this.gtin = product1.getGtin();
		this.product = product1.getProduct();
		this.price = product1.getPrice();
		this.status = product1.getStatus();
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
