/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import teoytea.models.dibujos.ColorEnum;

/**
 *
 * @author miguel
 */
public class DBbocadillosTest {
    
    public DBbocadillosTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of anhadirBocadilloToComp method, of class DBbocadillos.
     */
    @Test
    public void testAnhadirBocadilloToComp() {
        try {
            System.out.println("anhadirBocadilloToComp");
            int idcomp = 0;
            DBbocadillos inst = new DBbocadillos();
            inst.deleteAllBocadillos(idcomp);
            long uno = inst.anhadirBocadilloToComp(0, "tipo1", "texto1", 10, 20, ColorEnum.Cenum.ROJO);
            long dos = inst.anhadirBocadilloToComp(0, "tipo2", "texto2", 11, 21, ColorEnum.Cenum.ROJO);
            long tres = inst.anhadirBocadilloToComp(0, "tipo3", "texto3", 12, 22, ColorEnum.Cenum.ROJO);
            
            ResultSet rs = inst.getBocadillo((int)dos);
            rs.last();
            assertEquals(1, rs.getRow());
            rs.beforeFirst();
            rs.next();
            assertEquals(11, rs.getInt("X"));
            assertEquals(21, rs.getInt("Y"));
            
            rs = inst.getAllBocadillos(idcomp);
            rs.last();
            assertEquals(3, rs.getRow());
            rs.beforeFirst();
            rs.next();
            assertEquals(10, rs.getInt("X"));
            assertEquals(20, rs.getInt("Y"));
            inst.actualizaPosicionBocadillo((int)uno, 30, 40);
            rs = inst.getAllBocadillos(idcomp);
            rs.next();
            assertEquals(30, rs.getInt("X"));
            assertEquals(40, rs.getInt("Y"));
            
            // fill panel
            JPanel panel = new JPanel();
            inst.fillPanel(panel, idcomp);
            assertEquals(3, panel.getComponentCount());
            
            // borrar uno
            inst.borrarBocadilloDeComp((int)uno);
            rs = inst.getAllBocadillos(idcomp);
            rs.last();
            assertEquals(2, rs.getRow());
            
            // borrar todos
            inst.deleteAllBocadillos(idcomp);
            rs = inst.getAllBocadillos(idcomp);
            rs.last();
            assertEquals(0, rs.getRow());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBbocadillosTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
