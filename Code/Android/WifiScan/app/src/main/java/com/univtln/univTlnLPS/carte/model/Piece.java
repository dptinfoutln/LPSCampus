package com.univtln.univTlnLPS.carte.model;

import com.univtln.univTlnLPS.scan.model.ScanData;

import java.util.Set;

public class Piece {
    private int position_x;
    private int position_y;
    private long id;
    private Etage etage;
    private Set<ScanData> listScanData;

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public long getId() {
        return id;
    }

    public Etage getEtage() {
        return etage;
    }

    public Set<ScanData> getListScanData() {
        return listScanData;
    }

    public Piece(int position_x, int position_y, long id, Etage etage, Set<ScanData> listScanData) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.id = id;
        this.etage = etage;
        this.listScanData = listScanData;
    }

}
