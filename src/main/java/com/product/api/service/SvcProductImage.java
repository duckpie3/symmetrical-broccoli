package com.product.api.service;

import java.util.List;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;

public interface SvcProductImage {
	public List<DtoProductImageOut> getProductImages(Integer productId);
	public void uploadProductImage(DtoProductImageIn in);
	public void deleteProductImage(Integer productId, Integer productImageId);
}
