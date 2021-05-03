package com.univtln.univTlnLPS.dao.scan;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class ScanDataDAO extends AbstractDAO<ScanData> {
    public List<ScanData> findById(long id) {
        return getEntityManager().createNamedQuery("scanData.findById")
                .setParameter("id", id)
                .getResultList();
    }
}