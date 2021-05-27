package com.univtln.univTlnLPS.model.carte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.univtln.univTlnLPS.model.scan.ScanData;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    public void testCanEqual() {
        assertFalse((new Piece()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        Piece piece = new Piece();
        assertTrue(piece.canEqual(new Piece()));
    }

    @Test
    public void testConstructor() {
        Piece actualPiece = new Piece();
        Etage etage = new Etage();
        actualPiece.setEtage(etage);
        actualPiece.setId(123L);
        actualPiece.setName("Name");
        actualPiece.setPosition_x(1);
        actualPiece.setPosition_y(1);
        HashSet<ScanData> scanDataSet = new HashSet<ScanData>();
        actualPiece.setScanList(scanDataSet);
        assertSame(etage, actualPiece.getEtage());
        assertEquals(123L, actualPiece.getId());
        assertEquals("Name", actualPiece.getName());
        assertEquals(1, actualPiece.getPosition_x());
        assertEquals(1, actualPiece.getPosition_y());
        assertSame(scanDataSet, actualPiece.getScanList());
        assertEquals(
                "Piece(position_x=1, position_y=1, name=Name, id=123, etage=Etage(plan=null, name=null, id=0, batiment=null,"
                        + " pieceList=null), scanList=[])",
                actualPiece.toString());
    }

    @Test
    public void testConstructor2() {
        Etage etage = new Etage();
        Piece actualPiece = new Piece(1, 1, "Name", 123L, etage, new HashSet<ScanData>());
        assertEquals(
                "Piece(position_x=1, position_y=1, name=Name, id=123, etage=Etage(plan=null, name=null, id=0, batiment=null,"
                        + " pieceList=null), scanList=[])",
                actualPiece.toString());
        assertEquals(123L, actualPiece.getId());
        assertEquals(1, actualPiece.getPosition_x());
        assertEquals(1, actualPiece.getPosition_y());
        assertEquals("Name", actualPiece.getName());
        assertTrue(actualPiece.getScanList().isEmpty());
    }

    @Test
    public void testEquals() {
        assertFalse((new Piece()).equals("42"));
    }

    @Test
    public void testEquals2() {
        Piece piece = new Piece();
        assertTrue(piece.equals(new Piece()));
    }

    @Test
    public void testEquals3() {
        Piece piece = new Piece();
        Etage etage = new Etage();
        assertFalse(piece.equals(new Piece(1, 1, "Name", 123L, etage, new HashSet<ScanData>())));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new Piece()).hashCode());
    }
}

