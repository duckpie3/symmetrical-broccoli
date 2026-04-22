package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.service.SvcProduct;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class CtrlProduct {

	@Autowired
	SvcProduct svc;

	@GetMapping
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		return ResponseEntity.ok(svc.getProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DtoProductOut> getProduct(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.getProduct(id));
	}

	@PostMapping
	public ResponseEntity<String> createProduct(@Valid @RequestBody DtoProductIn in) {
		svc.createProduct(in);
		return new ResponseEntity<>("El producto ha sido registrado", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable Integer id, @Valid @RequestBody DtoProductIn in) {
		svc.updateProduct(id, in);
		return new ResponseEntity<>("El producto ha sido actualizado", HttpStatus.OK);
	}

	@PatchMapping("/{id}/enable")
	public ResponseEntity<String> enableProduct(@PathVariable Integer id) {
		svc.enableProduct(id);
		return new ResponseEntity<>("El producto ha sido activado", HttpStatus.OK);
	}

	@PatchMapping("/{id}/disable")
	public ResponseEntity<String> disableProduct(@PathVariable Integer id) {
		svc.disableProduct(id);
		return new ResponseEntity<>("El producto ha sido desactivado", HttpStatus.OK);
	}
}
