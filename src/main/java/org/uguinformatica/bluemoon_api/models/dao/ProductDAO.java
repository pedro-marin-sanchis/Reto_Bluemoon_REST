package org.uguinformatica.bluemoon_api.models.dao;

import org.uguinformatica.bluemoon_api.models.entity.Product;

public interface ProductDAO {

    void save(Product product);

    Product findById(long id);

    Product[] findAll();

    void update(Product product);

    void delete(long id);

}
