package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Order;

public interface OrderDAO {
    void save(Order order);

    Order findById(long id);

    Order[] findAll();

    void update(Order order);

    void delete(long id);
}
