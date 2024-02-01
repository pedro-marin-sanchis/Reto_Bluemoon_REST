package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Trade;

import java.util.List;

public interface TradeDAO {

    void save(Trade trade);

    Trade findById(long id);

    List<Trade> findAll();

    void update(Trade trade);

    void delete(long id);
}
