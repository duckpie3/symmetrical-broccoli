package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

	@Autowired
	SvcCategory svc;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.ok(svc.findAll());
	}

	@GetMapping("/active")
	public ResponseEntity<List<Category>> findActive() {
		return ResponseEntity.ok(svc.findActive());
	}

	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody DtoCategoryIn in) {
		svc.create(in);
		return ResponseEntity.ok("La categoria ha sido registrada.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody DtoCategoryIn in) {
		svc.update(in, id);
		return ResponseEntity.ok("La categoria ha sido actualizada.");
	}

	@PatchMapping("/{id}/enable")
	public ResponseEntity<String> enable(@PathVariable Integer id) {
		svc.enable(id);
		return ResponseEntity.ok("La categoria ha sido activada.");
	}

	@PatchMapping("/{id}/disable")
	public ResponseEntity<String> disable(@PathVariable Integer id) {
		svc.disable(id);
		return ResponseEntity.ok("La categoria ha sido desactivada.");
	}

}
