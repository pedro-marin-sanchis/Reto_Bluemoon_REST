package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Trade;

public interface TradeDAO {

    void save(Trade trade);

    Trade findById(long id);

    Trade[] findAll();

    void update(Trade trade);

    void delete(long id);
}
