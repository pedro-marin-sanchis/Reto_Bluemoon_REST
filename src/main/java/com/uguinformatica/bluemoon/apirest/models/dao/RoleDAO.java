package com.uguinformatica.bluemoon.apirest.models.dao;

import com.uguinformatica.bluemoon.apirest.models.entity.Role;

public interface RoleDAO {
    Role findByRoleName(String roleName);
}
