package org.uguinformatica.bluemoon_api.models.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.uguinformatica.bluemoon_api.models.entity.Tradeable;

@Repository
public class TradeableDAOImpl implements TradeableDAO {
    private EntityManager entityManager;

    @Autowired
    public TradeableDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Tradeable tradeable) {
        entityManager.persist(tradeable);
    }

    public Tradeable findById(long id) {
        return entityManager.find(Tradeable.class, id);
    }

    public Tradeable[] findAll() {
        Query query = entityManager.createQuery("from Tradeable ");
        return (Tradeable[]) query.getResultList().toArray();
    }

    public void update(Tradeable tradeable) {
        entityManager.merge(tradeable);
    }

    public void delete(long id) {
        Tradeable tradeable = findById(id);
        entityManager.remove(tradeable);
    }
}
