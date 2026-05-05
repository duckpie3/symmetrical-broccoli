package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DtoUserIn {

    @JsonProperty("username")
    @NotNull(message = "El username es obligatorio")
    private String username;

    @JsonProperty("email")
    @Email(message = "El email tiene un formato invalido")
    @NotNull(message = "El email es obligatorio")
    private String email;

    @JsonProperty("password")
    @NotNull(message = "El password es obligatorio")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@_$#!%*?&]).{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, una mayúscula, un número y un carácter especial (@$!%*?&)")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
