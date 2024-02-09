package com.uguinformatica.bluemoon.apirest.models.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.ToString;

import java.util.List;

@ToString
public class UserTradeCreateEntity {

    @NotEmpty(message = "The trade must have at least one tradeable")
    @Valid
    public List<TradeableCreateEntity> tradeables;
}
