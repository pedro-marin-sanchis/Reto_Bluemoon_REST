package org.uguinformatica.bluemoon_api.models.dao;

import org.uguinformatica.bluemoon_api.models.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void save(User user){
        entityManager.persist(user);
    }

    public User findById(long id){
        return entityManager.find(User.class, id);
    }

    public User[] findAll(){
        Query query = entityManager.createQuery("from User ");
        return (User[]) query.getResultList().toArray();
    }

    public void update(User user){
        entityManager.merge(user);
    }

    public void delete(long id){
        User user = findById(id);
        entityManager.remove(user);
    }
}