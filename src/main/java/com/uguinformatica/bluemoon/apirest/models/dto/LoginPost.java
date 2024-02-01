package com.uguinformatica.bluemoon.apirest.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class LoginPost {
    @NotEmpty(message = "The username must not be empty")
    public String username;

    @NotEmpty(message = "The password must not be empty")
    public String password;
}
