package org.uguinformatica.bluemoon_api.models.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.uguinformatica.bluemoon_api.models.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO{
    private EntityManager entityManager;

    @Autowired
    public ProductDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void save(Product product){
        entityManager.persist(product);
    }

    public Product findById(long id){
        return entityManager.find(Product.class, id);
    }

    public Product[] findAll(){
        Query query = entityManager.createQuery("from Product ");
        return (Product[]) query.getResultList().toArray();
    }

    public void update(Product product){
        entityManager.merge(product);
    }

    public void delete(long id){
        Product product = findById(id);
        entityManager.remove(product);
    }

}