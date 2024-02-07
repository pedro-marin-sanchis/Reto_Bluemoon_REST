package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.CartItem;
import com.uguinformatica.bluemoon.apirest.models.entity.keys.CartKey;

import java.util.List;

public interface CartDAO {

    void save(CartItem cartItem);

    CartItem findByKey(CartKey cartKey);

    void delete(CartItem cartItem);

    void deleteAllByUser(long userId);

    void update(CartItem cartItem);


}
