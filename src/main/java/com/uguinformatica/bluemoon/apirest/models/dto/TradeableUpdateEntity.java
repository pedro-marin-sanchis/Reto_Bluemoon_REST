package com.uguinformatica.bluemoon.apirest.models.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TradeableUpdateEntity {

    @DecimalMin(value = "0.0", inclusive = false, message = "The weight must be greater than 0")
    public double weight;
    @DecimalMin(value = "0.0", inclusive = false, message = "The sell price must be greater than 0")
    public double sellPrice;

    @NotEmpty(message = "The description must not be empty")
    public String description;

    @NotNull(message = "The silver type must be valid")
    @Min(value = 1, message = "The silver type id must be a valid id")
    public int silverTypeId;
}
