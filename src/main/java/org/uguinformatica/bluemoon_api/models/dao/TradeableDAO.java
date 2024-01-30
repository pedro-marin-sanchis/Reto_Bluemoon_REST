package org.uguinformatica.bluemoon_api.models.dao;

import org.uguinformatica.bluemoon_api.models.entity.Tradeable;

public interface TradeableDAO {

    void save(Tradeable tradeable);

    Tradeable findById(long id);

    Tradeable[] findAll();

    void update(Tradeable tradeable);

    void delete(long id);
}
