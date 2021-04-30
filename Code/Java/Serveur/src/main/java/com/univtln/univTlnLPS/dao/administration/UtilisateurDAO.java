package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Utilisateur;

import lombok.AccessLevel;
        import lombok.RequiredArgsConstructor;
        import lombok.experimental.FieldDefaults;

        import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class UtilisateurDAO extends AbstractDAO<Utilisateur> {
    public List<Utilisateur> findByName(long id) {
        return getEntityManager().createNamedQuery("utilisateur.findByID")
                .setParameter("id", id)
                .getResultList();
    }
}