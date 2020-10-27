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

/**
 *
 * @author miguel
 */
public class DBPictogramasTest {
    
    public DBPictogramasTest() {
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
     * Test of getAllPictos method, of class DBPictogramas.
     */
    @Test
    public void testGetAllPictos_int() {
        try {
            int idcomp = 0;
            // get all pictos
            DBPictogramas inst = new DBPictogramas (new JPanel(), idcomp);
            inst.deleteAllPictos(idcomp);
            ResultSet rs = inst.getAllPictos();
            rs.last();
            assertEquals(0, rs.getRow());
            
            // anhadir Picto To Comp
            long uno = inst.anhadirPictoToComp(idcomp, 5422, 10, 20, 50, 60);
            long dos = inst.anhadirPictoToComp(idcomp, 5422, 11, 21, 51, 61);
            long tres = inst.anhadirPictoToComp(idcomp, 5422, 12, 22, 52, 62);
            
            // get All Pictos
            rs = inst.getAllPictos();
            rs.last();
            assertEquals(3, rs.getRow());
            
            // fill panel
            JPanel panel = new JPanel();
            inst.fillPanel(panel);
            assertEquals(3, panel.getComponentCount());
            
            // actualiza posicion picto
            rs.beforeFirst();
            rs.next();
            assertEquals(10, rs.getInt("X"));
            assertEquals(20, rs.getInt("Y"));
            inst.actualizaPosicionPicto((int)uno, 30, 40);
            rs = inst.getAllPictos();
            rs.next();
            assertEquals(30, rs.getInt("X"));
            assertEquals(40, rs.getInt("Y"));
            
            // borrar picto de comp
            inst.borrarPictoDeComp((int)uno);
            
            // get all pictos
            rs = inst.getAllPictos();
            rs.last();
            assertEquals(2, rs.getRow());
            
            // delete all pictos
            inst.deleteAllPictos(idcomp);
            
            // get all pictos
            rs = inst.getAllPictos();
            rs.last();
            assertEquals(0, rs.getRow());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramasTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    
}
