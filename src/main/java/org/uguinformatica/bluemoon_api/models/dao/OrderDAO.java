package org.uguinformatica.bluemoon_api.models.dao;

import org.uguinformatica.bluemoon_api.models.entity.Order;

public interface OrderDAO {
    void save(Order order);

    Order findById(long id);

    Order[] findAll();

    void update(Order order);

    void delete(long id);
}