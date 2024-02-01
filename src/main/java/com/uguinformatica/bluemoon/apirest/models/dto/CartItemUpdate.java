package com.uguinformatica.bluemoon.apirest.models.dto;

import jakarta.validation.constraints.Min;

public class CartItemUpdate {

    @Min(value = 1, message = "Invalid quantity")
    public int quantity;
}
