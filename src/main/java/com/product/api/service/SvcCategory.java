package com.product.api.service;

import java.util.List;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.dto.out.DtoCategoryOut;

public interface SvcCategory {

	public List<DtoCategoryOut> findAll();

	public List<DtoCategoryOut> findActive();

	public void create(DtoCategoryIn in);

	public void update(DtoCategoryIn in, Integer id);

	public void enable(Integer id);

	public void disable(Integer id);

}
