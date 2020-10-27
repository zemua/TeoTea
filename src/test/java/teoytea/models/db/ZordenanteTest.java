/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import teoytea.models.DrawPaneTH;
import teoytea.models.dibujos.ColorEnum;

/**
 *
 * @author miguel
 */
public class ZordenanteTest {
    
    public ZordenanteTest() {
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
     * Test of setOrdenZs y ordenaZs method, of class Zordenante.
     */
    @Test
    public void testSetOrdenZs() {
        int idcomp = 0;
        Zordenante zs = new Zordenante();
        DBbocadillos db = new DBbocadillos();
        db.deleteAllBocadillos(idcomp);
        
        long uno = db.anhadirBocadilloToComp(idcomp, "tipo1", "texto1", idcomp, idcomp, ColorEnum.Cenum.ROJO);
        long dos = db.anhadirBocadilloToComp(idcomp, "tipo1", "texto1", idcomp, idcomp, ColorEnum.Cenum.ROJO);
        
        JLabel l1 = new JLabel();
        DrawPaneTH.setLabelDbId(l1, (int)uno);
        JLabel l2 = new JLabel();
        DrawPaneTH.setLabelDbId(l2, (int)dos);
        
        JPanel panel = new JPanel();
        panel.add(l1);
        panel.add(l2);
        
        panel.setComponentZOrder(l1, 0);
        panel.setComponentZOrder(l2, 1);
        
        zs.setOrdenZs(panel);
        
        assertEquals(0, panel.getComponentZOrder(l1));
        assertEquals(1, panel.getComponentZOrder(l2));
        
        panel.setComponentZOrder(l1, 1);
        panel.setComponentZOrder(l2, 0);
        
        assertEquals(1, panel.getComponentZOrder(l1));
        assertEquals(0, panel.getComponentZOrder(l2));
        
        zs.ordenaZs(panel);
        
        assertEquals(0, panel.getComponentZOrder(l1));
        assertEquals(1, panel.getComponentZOrder(l2));
        
        panel.setComponentZOrder(l1, 1);
        panel.setComponentZOrder(l2, 0);
        
        assertEquals(1, panel.getComponentZOrder(l1));
        assertEquals(0, panel.getComponentZOrder(l2));
        
        zs.setOrdenZs(panel);
        zs.ordenaZs(panel);
        
        assertEquals(1, panel.getComponentZOrder(l1));
        assertEquals(0, panel.getComponentZOrder(l2));
        
        db.deleteAllBocadillos(idcomp);
    }
    
}
