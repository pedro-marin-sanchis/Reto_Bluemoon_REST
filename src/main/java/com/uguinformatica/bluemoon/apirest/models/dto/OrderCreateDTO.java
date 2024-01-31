package com.uguinformatica.bluemoon.apirest.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter

public class OrderCreateDTO {
    @NotEmpty(message = "No user specified")
    public long userId;
}
