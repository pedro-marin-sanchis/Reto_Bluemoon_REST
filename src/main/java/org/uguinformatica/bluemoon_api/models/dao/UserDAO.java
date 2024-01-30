package org.uguinformatica.bluemoon_api.models.dao;


import org.uguinformatica.bluemoon_api.models.entity.User;

interface UserDAO {
    void save(User user);

    User findById(long id);

    User[] findAll();

    void update(User user);

    void delete(long id);


}
