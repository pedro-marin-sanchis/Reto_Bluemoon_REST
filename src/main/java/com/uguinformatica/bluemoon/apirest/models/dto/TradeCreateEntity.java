package com.uguinformatica.bluemoon.apirest.models.dto;


import com.uguinformatica.bluemoon.apirest.models.entity.Tradeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.ToString;

import java.util.List;

@ToString
public class TradeCreateEntity {

    @Min(value = 1, message = "The id must be valid")
    public long userId;

    @NotEmpty(message = "The trade must have at least one tradeable")
    @Valid
    public List<TradeableCreateEntity> tradeables;
}
