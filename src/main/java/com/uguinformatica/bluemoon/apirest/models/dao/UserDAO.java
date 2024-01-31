package com.uguinformatica.bluemoon.apirest.models.dao;


import com.uguinformatica.bluemoon.apirest.models.entity.User;

import java.util.List;

interface UserDAO {
    void save(User user);

    User findById(long id);

    List<User> findAll();

    void update(User user);

    void delete(long id);


}
