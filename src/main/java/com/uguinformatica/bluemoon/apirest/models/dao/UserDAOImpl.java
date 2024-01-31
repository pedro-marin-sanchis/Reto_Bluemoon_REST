package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.User;
import jakarta.persistence.NoResultException;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    public User findByUsername(String username) {
        try {
            Query query = entityManager.createQuery("from User where username = :username");
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            Query query = entityManager.createQuery("from User where email = :email");
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public List<User> findAll() {
        Query query = entityManager.createQuery("from User");
        return query.getResultList();
    }

    @Transactional
    public void update(User user) {
        System.out.println("UserDAOImpl.update");
        entityManager.merge(user);
    }

    @Transactional
    public void delete(long id) {
        User user = findById(id);
        entityManager.remove(user);
    }
}
