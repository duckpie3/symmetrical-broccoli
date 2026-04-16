package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.service.SvcProductImage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/{id}/image")
public class CtrlProductImage {
	@Autowired
	SvcProductImage svc;
	
	@GetMapping
	public ResponseEntity<List<DtoProductImageOut>> getProductImages(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.getProductImages(id));
	}
	
	@PostMapping
	public ResponseEntity<String> createProductImage(@Valid @RequestBody DtoProductImageIn in) {
		svc.uploadProductImage(in);
		return ResponseEntity.ok("La imagen ha sido registrada.");
	}
	
	@DeleteMapping("/{product_image_id}")
	public ResponseEntity<String> deleteProductImage(@PathVariable Integer id, @PathVariable Integer product_image_id) {
		return ResponseEntity.ok(null);
		// TODO: return ResponseEntity.ok(svc.uploadProductImages(id, image))
	}
}
