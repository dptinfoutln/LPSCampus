package com.univtln.univTlnLPS.dao;

import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.net.server.LPSServer;
import lombok.Getter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import java.lang.reflect.ParameterizedType;

@Getter
public abstract class AbstractDAO<T extends SimpleEntity> implements DAO<T> {

    private Class<T> myType;

    private EntityManager entityManager = Persistence
            .createEntityManagerFactory(LPSServer.properties.getProperty("pu")).createEntityManager();

    public AbstractDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.myType = (Class) genericSuperclass.getActualTypeArguments()[0];
    }
}
