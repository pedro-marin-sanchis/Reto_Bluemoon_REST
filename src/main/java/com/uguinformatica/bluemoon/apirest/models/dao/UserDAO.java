package com.uguinformatica.bluemoon.apirest.models.dao;


import com.uguinformatica.bluemoon.apirest.models.entity.User;

interface UserDAO {
    void save(User user);

    User findById(long id);

    User[] findAll();

    void update(User user);

    void delete(long id);


}
