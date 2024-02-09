package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.ProductOrder;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface ProductOrderDAO {

    void save (ProductOrder orderProduct);

}
