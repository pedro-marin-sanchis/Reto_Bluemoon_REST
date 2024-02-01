package com.uguinformatica.bluemoon.apirest.models.dto;


import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class TradeUpdateEntity {



    @NotNull(message = "The tradeable must have a validation status")
    public Boolean validated;

}
