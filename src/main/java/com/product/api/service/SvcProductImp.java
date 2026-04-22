package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.repository.RepoProduct;
import com.product.common.mapper.MapperProduct;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImp implements SvcProduct {

	@Autowired
	RepoProduct repo;

	@Autowired
	MapperProduct mapper;

	@Override
	public List<DtoProductListOut> getProducts() {
		try {
			List<Product> products = repo.findAll();
			return mapper.fromProductList(products);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public DtoProductOut getProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.getProduct(id);
			return new DtoProductOut(product);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public void createProduct(DtoProductIn in) {
		try {
			Product product = mapper.fromDto(in);
			repo.save(product);
		} catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			throw new DBAccessException(e);
		}
	}

	@Override
	public void updateProduct(Integer id, DtoProductIn in) {
		try {
			validateProductId(id);
			Product product = mapper.fromDto(id, in);
			repo.save(product);
		} catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			throw new DBAccessException(e);
		}
	}

	@Override
	public void enableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(1);
			repo.save(product);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public void disableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(0);
			repo.save(product);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	private void validateProductId(Integer id) {
		try {
			if (repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

}
