package com.uguinformatica.bluemoon.apirest.models.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class TradeableUpdateEntity  {



    @NotNull(message = "The tradeable must have a validation status")
    public Boolean validated;

}
