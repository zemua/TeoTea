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
public class DBFlechasTest {
    
    public DBFlechasTest() {
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
     * Test of all methods, of class DBFlechas.
     */
    @Test
    public void testAllFlechas() {
        try {
            int idcomp = 0;
            DBFlechas inst = new DBFlechas();
            inst.deleteAllFlechas(idcomp);
            ResultSet rs = inst.getAllFlechas(idcomp); // get all flechas sin introducir ninguna
            rs.last();
            assertEquals(0, rs.getRow());
            rs.beforeFirst();
            
            long uno = inst.anhadirFlechaToComp(idcomp, 0, 15, 5, 50, 10, 20, ColorEnum.Cenum.AMARILLO);
            long dos = inst.anhadirFlechaToComp(idcomp, 0, 15, 5, 50, 11, 21, ColorEnum.Cenum.AMARILLO);
            long tres = inst.anhadirFlechaToComp(idcomp, 0, 15, 5, 50, 12, 22, ColorEnum.Cenum.AMARILLO);
            
            // get one
            rs = inst.getUnaFlecha((int)dos);
            rs.last();
            assertEquals(1, rs.getRow());
            rs.beforeFirst();
            rs.next();
            assertEquals(11, rs.getInt("X"));
            assertEquals(21, rs.getInt("Y"));
            assertEquals((int)dos, rs.getInt("ID"));
            
            // Get all
            rs = inst.getAllFlechas(idcomp);
            rs.last();
            assertEquals(3, rs.getRow());
            rs.beforeFirst();
            
            // actualizar posicion flecha
            rs.next();
            int x = rs.getInt("X");
            int y = rs.getInt("Y");
            long i = rs.getLong("ID");
            assertEquals(10, x);
            assertEquals(20, y);
            assertEquals(uno, i);
            inst.actualizaPosicionFlecha((int)i, 30, 40);
            rs = inst.getAllFlechas(idcomp);
            rs.last();
            assertEquals(3, rs.getRow());
            rs.beforeFirst();
            rs.next();
            x = rs.getInt("X");
            y = rs.getInt("Y");
            i = rs.getLong("ID");
            assertEquals(30, x);
            assertEquals(40, y);
            assertEquals(uno, i);
            
            // fill panel
            JPanel panel = new JPanel();
            inst.fillPanel(panel, idcomp);
            assertEquals(3, panel.getComponentCount());
            
            // borrar flecha de comp
            inst.borrarFlechaDeComp((int)uno);
            rs = inst.getAllFlechas(idcomp);
            rs.last();
            assertEquals(2, rs.getRow());
            rs.beforeFirst();
            
            // borrar all flechas
            inst.borrarFlechaDeComp(idcomp);
            rs = inst.getAllFlechas(idcomp);
            rs.last();
            assertEquals(0, rs.getRow());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBFlechasTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
