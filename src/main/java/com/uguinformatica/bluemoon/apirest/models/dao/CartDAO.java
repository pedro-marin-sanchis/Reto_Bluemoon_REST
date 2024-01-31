package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.CartItem;

import java.util.List;

public interface CartDAO {

    void save(CartItem cartItem);

    void delete(CartItem cartItem);

    void update(CartItem cartItem);

    List<CartItem> findByUserId(long userId);

}
