package org.uguinformatica.bluemoon_api.models.dao;

import org.uguinformatica.bluemoon_api.models.entity.Trade;

public interface TradeDAO {

    void save(Trade trade);

    Trade findById(long id);

    Trade[] findAll();

    void update(Trade trade);

    void delete(long id);
}
