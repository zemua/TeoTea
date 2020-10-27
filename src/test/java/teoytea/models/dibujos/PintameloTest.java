/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Point;
import javax.swing.JLabel;
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
public class PintameloTest {
    
    public PintameloTest() {
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
     * Test of setColor method, of class Pintamelo.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        ColorEnum.Cenum i = ColorEnum.Cenum.AMARILLO;
        Pintamelo.setColor(i);
        assertEquals(i, Pintamelo.getColor());
        i = ColorEnum.Cenum.VERDE;
        Pintamelo.setColor(i);
        assertEquals(i, Pintamelo.getColor());
        i = ColorEnum.Cenum.AMARILLO;
        Pintamelo.setColor(i);
        assertEquals(i, Pintamelo.getColor());
        i = ColorEnum.Cenum.AZUL;
        Pintamelo.setColor(i);
        assertEquals(i, Pintamelo.getColor());
        i = ColorEnum.Cenum.NEGRO;
        Pintamelo.setColor(i);
        assertEquals(i, Pintamelo.getColor());
    }

    /**
     * Test of meLoPintas method, of class Pintamelo.
     */
    @Test
    public void testMeLoPintas() {
        System.out.println("meLoPintas");
        Point inicio = new Point(25, 25);
        Point termina = new Point(50, 55);
        int tipo = 0;
        // uno
        JLabel mlabel = new JLabel();
        Pintamelo.meLoPintas(inicio, termina, tipo, mlabel);
        assertEquals(29, mlabel.getBounds().getWidth());
        assertEquals(33, mlabel.getBounds().getHeight());
        // otro
        inicio = new Point (50, 55);
        termina = new Point (25, 25);
        Pintamelo.meLoPintas(inicio, termina, tipo, mlabel);
        assertEquals(29, mlabel.getBounds().getWidth());
        assertEquals(33, mlabel.getBounds().getHeight());
        // otro
        inicio = new Point (25, 55);
        termina = new Point (50, 25);
        Pintamelo.meLoPintas(inicio, termina, tipo, mlabel);
        assertEquals(29, mlabel.getBounds().getWidth());
        assertEquals(33, mlabel.getBounds().getHeight());
        // otro
        inicio = new Point (50, 25);
        termina = new Point (25, 55);
        Pintamelo.meLoPintas(inicio, termina, tipo, mlabel);
        assertEquals(29, mlabel.getBounds().getWidth());
        assertEquals(33, mlabel.getBounds().getHeight());
    }

    /**
     * Test of labelToPanel method, of class Pintamelo.
     */
    @Test
    public void testLabelToPanel() {
        System.out.println("labelToPanel");
        JLabel label = new JLabel();
        JLabel label2 = new JLabel();
        JPanel panel = new JPanel();
        Pintamelo.labelToPanel(label, panel);
        assertEquals(1, panel.getComponentCount());
        assertEquals(0, panel.getComponentZOrder(label));
        Pintamelo.labelToPanel(label2, panel);
        assertEquals(2, panel.getComponentCount());
        assertEquals(0, panel.getComponentZOrder(label2));
        assertEquals(1, panel.getComponentZOrder(label));
    }
    
}
