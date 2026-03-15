package com.product.api.service;

import java.util.List;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

public interface SvcCategory {

	public List<Category> findAll();

	public List<Category> findActive();

	public void create(DtoCategoryIn in);

	public void update(DtoCategoryIn in, Integer id);

	public void enable(Integer id);

	public void disable(Integer id);

}
