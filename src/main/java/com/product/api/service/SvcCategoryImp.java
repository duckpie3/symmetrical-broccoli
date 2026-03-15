package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

import jakarta.servlet.http.HttpSession;

@Service
public class SvcCategoryImp implements SvcCategory {
	@Autowired
	RepoCategory repo;

	@Override
	public List<Category> findAll() {
		try {
			return repo.findAll();
		} catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos.");
		}

	}

	@Override
	public List<Category> findActive() {
		try {
			return repo.findByStatus(1);
		} catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos.");
		}
	}

	@Override
	public void create(DtoCategoryIn in) {
		try {
			repo.create(in.getCategory(), in.getTag());
		} catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_category")) {
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoria ya esta registrado.");
			}
			if (e.getLocalizedMessage().contains("ux_tag")) {
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoria ya esta registrado.");
			}

			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos.");
		}
	}

	@Override
	public void update(DtoCategoryIn in, Integer id) {
		try {
			repo.update(id, in.getCategory(), in.getTag());
		} catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_category")) {
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoria ya esta registrado.");
			}
			if (e.getLocalizedMessage().contains("ux_tag")) {
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoria ya esta registrado.");
			}

			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos.");
		}
	}

	@Override
	public void enable(Integer id) {
		try {
			validateId(id);
			repo.updateStatus(id, 1);
		} catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos.");
		}
	}

	@Override
	public void disable(Integer id) {
		try {
			validateId(id);
			repo.updateStatus(id, 0);
		} catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos.");
		}
	}

	private void validateId(Integer id) {
		if (repo.findById(id).isEmpty()) {
			throw new ApiException(HttpStatus.NOT_FOUND, "No existe categoria con id " + id);
		}
	}

}
