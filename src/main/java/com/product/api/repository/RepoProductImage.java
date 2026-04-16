package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.api.entity.ProductImage;

public interface RepoProductImage extends JpaRepository<ProductImage, Integer> {
	
//	@Query(value = "SELECT * FROM product_image WHERE product_id = :product_id AND status = 1;", nativeQuery=true)
//	ProductImage[] 
   List<ProductImage> findByProductId(Integer productId);

}
