package com.product.api.service;

import com.product.api.entity.User;
import com.product.api.dto.in.DtoUserIn;
import com.product.api.dto.out.DtoUserOut;
import com.product.api.repository.RepoUser;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SvcUserImp implements SvcUser {

    @Autowired
    RepoUser repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void register(DtoUserIn in) {
        try {
            User newUser = new User(in.getUsername(), in.getEmail(), in.getPassword(), 1);
            newUser.setPassword(passwordEncoder.encode(in.getPassword()));
            repo.save(newUser);
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_user_username"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de usuario ya está registrado");
            if (e.getLocalizedMessage().contains("ux_user_email"))
                throw new ApiException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
            throw e;
        }

    }

    @Override
    public void update(DtoUserIn in, Integer id) {
        try {
            validateId(id);
            User user = repo.findById(id).get();
            user.setUsername(in.getUsername());
            user.setEmail(in.getEmail());
            user.setPassword(passwordEncoder.encode(in.getPassword()));
            repo.save(user);
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_user_username"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de usuario ya está registrado");
            if (e.getLocalizedMessage().contains("ux_user_email"))
                throw new ApiException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
            throw e;
        }

    }

    @Override
    public void enable(Integer id) {
        try {
            validateId(id);
            User user = repo.findById(id).get();
            user.setStatus(1);
            repo.save(user);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Override
    public void disable(Integer id) {
        try {
            validateId(id);
            User user = repo.findById(id).get();
            user.setStatus(0);
            repo.save(user);
        } catch (DataAccessException e) {
            throw e;
        }

    }

    @Override
    public DtoUserOut getUser(Integer id) {
        try {
            validateId(id);
            User user = repo.findById(id).get();
            return new DtoUserOut(user);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void validateId(Integer id) {
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No existe usuario con id " + id);
        }

    }

}
