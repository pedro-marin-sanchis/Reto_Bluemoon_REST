package org.uguinformatica.bluemoon_api.models.dao;

import org.uguinformatica.bluemoon_api.models.entity.SilverType;

public interface SilverTypeDAO {

    void save(SilverType sylverType);

    SilverType findById(long id);

    SilverType[] findAll();

    void update(SilverType sylverType);

    void delete(long id);
}