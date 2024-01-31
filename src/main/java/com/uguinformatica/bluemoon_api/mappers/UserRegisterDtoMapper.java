package com.uguinformatica.bluemoon_api.mappers;

import com.uguinformatica.bluemoon_api.models.dto.UserRegisterDTO;
import com.uguinformatica.bluemoon_api.models.entity.User;

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
