package org.uguinformatica.bluemoon_api.models.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.uguinformatica.bluemoon_api.models.entity.Trade;

@Repository
public class TradeDAOImpl implements TradeDAO{

    private EntityManager entityManager;

    @Autowired
    public TradeDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void save(Trade trade){
        entityManager.persist(trade);
    }

    public Trade findById(long id){
        return entityManager.find(Trade.class, id);
    }

    public Trade[] findAll(){
        Query query = entityManager.createQuery("from Trade ");
        return (Trade[]) query.getResultList().toArray();
    }

    public void update(Trade trade){
        entityManager.merge(trade);
    }

    public void delete(long id){
        Trade trade = findById(id);
        entityManager.remove(trade);
    }
}