package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Product;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {
	@Query(value = "SELECT p.*, c.category "
			+ "FROM product p "
			+ "INNER JOIN category c ON c.category_id = p.category_id "
			+ "WHERE p.product_id = :productId;", nativeQuery = true)
	Product getProduct(@Param("productId") Integer productId);
}
