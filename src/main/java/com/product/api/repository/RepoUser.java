package com.product.api.repository;

import com.product.api.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUser extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    User findByUserId(Integer userId);

    List<User> findByStatus(Integer status);

}
