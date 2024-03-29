package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private EntityManager entityManager;

    @Autowired
    public ProductDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional

    public void save(Product product) {
        entityManager.persist(product);
    }

    public Product findById(long id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> findAll() {
        Query query = entityManager.createQuery("from Product ");
        return query.getResultList();
    }

    @Transactional

    public void update(Product product) {
        entityManager.merge(product);
    }

    @Transactional

    public void delete(long id) {
        Product product = findById(id);
        product.setDisabled(true);
        entityManager.merge(product);
    }

}
