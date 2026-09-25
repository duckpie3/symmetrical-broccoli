package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.api.entity.User;

public class DtoUserOut {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("status")
    private Integer status;

    public DtoUserOut(Integer userId, String username, String email, Integer status) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public DtoUserOut(User user) {
        super();
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.status = user.getStatus();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
