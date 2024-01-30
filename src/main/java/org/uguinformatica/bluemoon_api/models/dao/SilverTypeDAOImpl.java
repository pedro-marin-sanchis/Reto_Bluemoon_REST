package org.uguinformatica.bluemoon_api.models.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.uguinformatica.bluemoon_api.models.entity.SilverType;

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

    public SilverType[] findAll() {
        Query query = entityManager.createQuery("from SilverType ");
        return (SilverType[]) query.getResultList().toArray();
    }

    @Transactional

    public void update(SilverType sylverType) {
        entityManager.merge(sylverType);
    }

    @Transactional

    public void delete(long id) {
        SilverType sylverType = findById(id);
        entityManager.remove(sylverType);
    }

}
