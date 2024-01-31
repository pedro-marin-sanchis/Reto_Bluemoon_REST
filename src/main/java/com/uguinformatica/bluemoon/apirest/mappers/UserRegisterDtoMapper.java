package com.uguinformatica.bluemoon.apirest.mappers;

import com.uguinformatica.bluemoon.apirest.models.dto.UserRegisterDTO;
import com.uguinformatica.bluemoon.apirest.models.entity.User;

public class UserRegisterDtoMapper {

    public static User map(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setUsername(userRegisterDTO.username);
        user.setName(userRegisterDTO.name);
        user.setSurnames(userRegisterDTO.surnames);
        user.setEmail(userRegisterDTO.email);
        user.setAddress(userRegisterDTO.address);
        user.setPassword(userRegisterDTO.password);
        return user;
    }

}
