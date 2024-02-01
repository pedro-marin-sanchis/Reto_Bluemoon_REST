package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Tradeable;

import java.util.List;

public interface TradeableDAO {

    Tradeable save(Tradeable tradeable);

    Tradeable findById(long id);

    List<Tradeable> findAll();

    void update(Tradeable tradeable);

    void delete(long id);
}
