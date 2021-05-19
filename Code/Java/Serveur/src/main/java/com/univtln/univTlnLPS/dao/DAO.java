package com.univtln.univTlnLPS.dao;

import com.univtln.univTlnLPS.model.SimpleEntity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
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

    default void clear() { getEntityManager().clear(); }

    default void deleteAll(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<T> cd = cb.createCriteriaDelete(getMyType());
        cd.from(getMyType());

        Query allQuery = getEntityManager().createQuery(cd);
        allQuery.executeUpdate();
    }

    /**
     * Supprime de la table getMyType() toutes les entitées qui ont pour DTYPE getMyType,
     * cette fonction est utile uniquement en cas d'héritage avec SINGLE_TABLE.
     */
    default void deleteAllWithDTYPE(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<T> cd = cb.createCriteriaDelete(getMyType());
        Root<T> e = cd.from(getMyType());
        cd.where(cb.equal(e.type(), getMyType()));

        Query allQuery = getEntityManager().createQuery(cd);
        allQuery.executeUpdate();
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

    default void remove(T t) { getEntityManager().remove(t);}

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