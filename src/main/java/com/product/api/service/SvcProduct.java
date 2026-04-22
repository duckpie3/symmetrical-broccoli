package com.product.api.service;

import java.util.List;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;

public interface SvcProduct {

	public List<DtoProductListOut> getProducts();
	public DtoProductOut getProduct(Integer id);
	public void createProduct(DtoProductIn in);
	public void updateProduct(Integer id, DtoProductIn in);
	public void enableProduct(Integer id);
	public void disableProduct(Integer id);

}
