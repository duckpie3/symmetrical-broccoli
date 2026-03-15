package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class DtoCategoryIn {

	@JsonProperty("category")
	@NotNull(message = "La categoria es obligatoria.")
	public String category;
	@JsonProperty("tag")
	@NotNull(message = "El tag es obligatorio.")
	public Integer tag;
}
