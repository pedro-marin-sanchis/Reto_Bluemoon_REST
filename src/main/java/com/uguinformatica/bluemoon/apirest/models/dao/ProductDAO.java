package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Product;

import java.util.List;

public interface ProductDAO {

    void save(Product product);

    Product findById(long id);

    List<Product> findAll();

    void update(Product product);

    void delete(long id);

}
