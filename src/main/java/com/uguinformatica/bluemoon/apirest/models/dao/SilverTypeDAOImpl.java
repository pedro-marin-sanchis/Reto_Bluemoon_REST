package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.SilverType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SilverTypeDAOImpl {

    private EntityManager entityManager;

    @Autowired
    public SilverTypeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional

    public void save(SilverType sylverType) {
        entityManager.persist(sylverType);
    }

    public SilverType findById(long id) {
        return entityManager.find(SilverType.class, id);
    }

    public List<SilverType> findAll() {
        Query query = entityManager.createQuery("from SilverType ");
        return query.getResultList();
    }

    @Transactional

    public void update(SilverType sylverType) {
        entityManager.merge(sylverType);
    }

    @Transactional

    public void delete(long id) {
        SilverType sylverType = findById(id);
        sylverType.setDisabled(true);
        entityManager.merge(sylverType);

    }

}
