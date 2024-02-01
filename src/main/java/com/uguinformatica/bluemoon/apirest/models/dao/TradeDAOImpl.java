package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Trade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TradeDAOImpl implements TradeDAO{

    private EntityManager entityManager;

    @Autowired
    public TradeDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Transactional

    public void save(Trade trade){
        entityManager.persist(trade);
    }

    public Trade findById(long id){
        return entityManager.find(Trade.class, id);
    }

    public List<Trade> findAll(){
        Query query = entityManager.createQuery("from Trade ");
        return  query.getResultList();
    }
    @Transactional

    public void update(Trade trade){
        entityManager.merge(trade);
    }
    @Transactional

    public void delete(long id){
        Trade trade = findById(id);
        entityManager.remove(trade);
    }
}
