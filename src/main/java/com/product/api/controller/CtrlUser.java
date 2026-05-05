package com.product.api.controller;

import com.product.api.dto.in.DtoUserIn;
import com.product.api.dto.out.DtoUserOut;
import com.product.api.service.SvcUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class CtrlUser {

    @Autowired
    SvcUser svc;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody DtoUserIn in) {
        svc.register(in);
        return ResponseEntity.ok("El usuario ha sido registrado.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUserOut> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody DtoUserIn in) {
        svc.update(in, id);
        return ResponseEntity.ok("El usuario ha sido actualizado.");
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id) {
        svc.enable(id);
        return ResponseEntity.ok("El usuario ha sido activado.");
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id) {
        svc.disable(id);
        return ResponseEntity.ok("El usuario ha sido desactivado.");
    }

}
