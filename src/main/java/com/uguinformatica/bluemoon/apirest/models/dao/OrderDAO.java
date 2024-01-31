package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Order;

import java.util.List;

public interface OrderDAO {
    void save(Order order);

    Order findById(long id);

    List findAll();

    void update(Order order);

    void delete(long id);
}
