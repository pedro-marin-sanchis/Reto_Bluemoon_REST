package com.uguinformatica.bluemoon.apirest.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CartAddItem {

    @Min(value = 1, message = "Invalid product id")
    public long productId;

    @Min(value = 1, message = "Invalid quantity")
    public int quantity;
}
