package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Tradeable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TradeableDAOImpl implements TradeableDAO {
    private EntityManager entityManager;

    @Autowired
    public TradeableDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional

    public Tradeable save(Tradeable tradeable) {
        entityManager.persist(tradeable);
        entityManager.flush();
        return tradeable;
    }

    public Tradeable findById(long id) {
        return entityManager.find(Tradeable.class, id);
    }

    public List<Tradeable> findAll() {
        Query query = entityManager.createQuery("from Tradeable ");
        return query.getResultList();
    }
    @Transactional

    public void update(Tradeable tradeable) {
        entityManager.merge(tradeable);
    }
    @Transactional

    public void delete(long id) {
        Tradeable tradeable = findById(id);
        entityManager.remove(tradeable);
    }
}
