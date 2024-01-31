package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Tradeable;

public interface TradeableDAO {

    void save(Tradeable tradeable);

    Tradeable findById(long id);

    Tradeable[] findAll();

    void update(Tradeable tradeable);

    void delete(long id);
}
