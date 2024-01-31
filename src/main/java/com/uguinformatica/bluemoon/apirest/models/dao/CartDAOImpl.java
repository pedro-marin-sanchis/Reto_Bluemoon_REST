package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO{

    private final EntityManager entityManager;

    @Autowired
    public CartDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(CartItem cartItem) {
        entityManager.persist(cartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        entityManager.remove(cartItem);
    }

    @Override
    public void update(CartItem cartItem) {
        entityManager.merge(cartItem);
    }

    @Override
    public List<CartItem> findByUserId(long userId) {
        Query query = entityManager.createQuery("from CartItem where user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }


}
