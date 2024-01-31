package com.uguinformatica.bluemoon.apirest.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
    @Size(min = 4, message = "Username size must be greater than 4")
    @NotEmpty(message = "No username specified")
    public String username;

    @NotEmpty(message = "No name specified")
    public String name;

    public String surnames;

    @NotEmpty(message = "No email specified")
    public String email;


    public String address;

    @NotEmpty(message = "No password specified")
    public String password;
}
