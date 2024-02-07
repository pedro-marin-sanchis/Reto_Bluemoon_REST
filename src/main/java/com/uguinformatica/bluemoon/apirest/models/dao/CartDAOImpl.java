package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.CartItem;
import com.uguinformatica.bluemoon.apirest.models.entity.keys.CartKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CartDAOImpl implements CartDAO {

    private final EntityManager entityManager;

    @Autowired
    public CartDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(CartItem cartItem) {
        entityManager.persist(cartItem);
    }

    @Override
    @Transactional
    public void delete(CartItem cartItem) {
        entityManager.remove(cartItem);
    }

    @Override
    @Transactional
    public void deleteAllByUser(long userId) {
        Query query = entityManager.createQuery("DELETE FROM CartItem c WHERE user.id = :userId");
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(CartItem cartItem) {
        entityManager.merge(cartItem);
    }

    @Override
    @Transactional
    public CartItem findByKey(CartKey cartKey) {
        return entityManager.find(CartItem.class, cartKey);
    }
}
