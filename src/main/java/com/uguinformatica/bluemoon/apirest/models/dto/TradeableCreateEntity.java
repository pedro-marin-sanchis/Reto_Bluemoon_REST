package com.uguinformatica.bluemoon.apirest.models.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class TradeableCreateEntity {

    @DecimalMin(value = "0.0", inclusive = false, message = "The weight must be greater than 0")
    public double weight;

    @NotEmpty(message = "The tradeable must have a description")
    public String description;

    @NotNull(message = "The silver type must be valid")
    @Min(value = 1, message = "The silver type id must be a valid id")
    public long silverTypeId;
}
