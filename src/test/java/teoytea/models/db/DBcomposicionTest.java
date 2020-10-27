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
public class DBcomposicionTest {
    
    public DBcomposicionTest() {
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
     * Test of all, of class DBcomposicion and DBgaleria.
     */
    @Test
    public void testAll() {
        try {
            DBgaleria galeria = DBgaleria.getInstance();
            DBcomposicion composicion = new DBcomposicion();
            
            ResultSet rs = galeria.getAllDibujos();
            rs.last();
            int inicial = rs.getRow();
            
            long uno = galeria.createNewDibujo();
            long dos = galeria.createNewDibujo();
            long tres = galeria.createNewDibujo();
            
            rs = galeria.getAllDibujos();
            rs.last();
            assertTrue(rs.getRow() >= 3);
            assertTrue(rs.getRow() == inicial+3);
            
            rs.beforeFirst();
            rs.next();
            assertEquals("Nuevo Dibujo", rs.getString("NOMBRE"));
            
            composicion.actualizaComposicion("otro", (int)uno);
            rs = galeria.getAllDibujos();
            rs.next();
            assertEquals("otro", rs.getString("NOMBRE"));
            
            assertEquals("otro", composicion.getNombre((int)uno));
            assertEquals("Nuevo Dibujo", composicion.getNombre((int)dos));
            assertEquals("Nuevo Dibujo", composicion.getNombre((int)tres));
            
            composicion.borrarComp((int)uno);
            composicion.borrarComp((int)dos);
            composicion.borrarComp((int)tres);
            
            rs = galeria.getAllDibujos();
            rs.last();
            assertEquals(inicial, rs.getRow());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBcomposicionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
