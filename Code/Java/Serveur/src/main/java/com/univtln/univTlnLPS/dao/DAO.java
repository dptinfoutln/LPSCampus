package com.univtln.univTlnLPS.dao;

import com.univtln.univTlnLPS.model.SimpleEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public interface DAO<T extends SimpleEntity> extends AutoCloseable {

    EntityManager getEntityManager();

    Class<T> getMyType();

    default void persist(T t) {
        getEntityManager().persist(t);
    }

    default T merge(T t) {
        return getEntityManager().merge(t);
    }

    default void refresh(T t) {
        getEntityManager().refresh(t);
    }

    default void clear() {
        getEntityManager().clear();
    }

    default List<T> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getMyType());
        Root<T> rootEntry = cq.from(getMyType());
        CriteriaQuery<T> all = cq.select(rootEntry);

        TypedQuery<T> allQuery = getEntityManager().createQuery(all);
        return allQuery.getResultList();    }

    default T find(long id) {
        return getEntityManager().find(getMyType(), id);
    }

    @Override
    default void close() {
        getEntityManager().close();
    }

    default void flush() {
        getEntityManager().flush();
    }

    default EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }


}