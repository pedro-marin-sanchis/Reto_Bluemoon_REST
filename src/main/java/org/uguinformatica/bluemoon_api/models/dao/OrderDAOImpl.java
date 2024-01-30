package org.uguinformatica.bluemoon_api.models.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.uguinformatica.bluemoon_api.models.entity.Order;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final EntityManager entityManager;

    @Autowired
    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findById(long id) {
        return entityManager.find(Order.class, id);
    }

    public Order[] findAll() {
        Query query = entityManager.createQuery("from Order");
        return (Order[]) query.getResultList().toArray();
    }
    @Transactional

    public void update(Order order) {
        entityManager.merge(order);
    }
    @Transactional

    public void delete(long id) {
        Order order = findById(id);
        entityManager.remove(order);
    }
}
