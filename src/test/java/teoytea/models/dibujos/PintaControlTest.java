/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
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
public class PintaControlTest {

    public PintaControlTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        //assertEquals(null, PintaControl.getInstance());
        // comentado porque arroja diferentes resultados dependiendo del orden en que se hagan los tests
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
     * Test of getInstance method, of class PintaControl.
     */
    @Test
    public void testGetInstance_JButton_JToggleButton() {
        System.out.println("getInstance");
        JButton botonBorrar = new JButton();
        JToggleButton tb = new JToggleButton();
        PintaControl result = PintaControl.getInstance(botonBorrar, tb, 1);
        assertEquals(PintaControl.class, result.getClass());
        PintaControl result2 = PintaControl.getInstance();
        assertEquals(result, result2);
        PintaControl result3 = PintaControl.getInstance(botonBorrar, tb, 1);
        assertEquals(result, result3);
    }

    /**
     * Test of isMouseWithinComponent method, of class PintaControl.
     */
    @Test
    public void testIsMouseWithinComponent_Component() {
        // Test gráfico
    }

    /**
     * Test of ratonExited method, of class PintaControl.
     */
    @Test
    public void testRaton() {
        System.out.println("ratonExited");
        Component c = new JPanel();
        JButton botonBorrar = new JButton();
        JToggleButton tb = new JToggleButton();
        PintaControl instance = PintaControl.getInstance(botonBorrar, tb, 1);
        //instance.setJpanel(new JPanel());
        // entramos
        assertEquals(false, instance.getModoPintar());
        assertEquals(Cursor.DEFAULT_CURSOR, c.getCursor().getType());
        instance.ratonEntered(c);
        assertEquals(Cursor.DEFAULT_CURSOR, c.getCursor().getType());
        instance.entraModoPintar();
        instance.ratonEntered(c);
        assertEquals(Cursor.CROSSHAIR_CURSOR, c.getCursor().getType());
        instance.dejaModoPintar();
        instance.ratonEntered(c);
        assertEquals(Cursor.DEFAULT_CURSOR, c.getCursor().getType());
        instance.entraModoPintar();
        instance.ratonEntered(c);
        assertEquals(Cursor.CROSSHAIR_CURSOR, c.getCursor().getType());
        // clicked
        assertEquals(true, instance.getModoPintar());
        assertTrue(tb.getIcon() == null);
        instance.ratonClicked(c);
        assertEquals(false, instance.getModoPintar());
        assertEquals(Cursor.DEFAULT_CURSOR, c.getCursor().getType());
        assertTrue(tb.getIcon() != null);
        // moved
        instance.entraModoPintar();
        instance.ratonEntered(c);
        assertEquals(Cursor.CROSSHAIR_CURSOR, c.getCursor().getType());
        instance.entraModoPintar();
        assertTrue(instance.getModoPintar());
        instance.ratonMoved(c);
        assertEquals(Cursor.CROSSHAIR_CURSOR, c.getCursor().getType());
        instance.dejaModoPintar();
        assertTrue(!instance.getModoPintar());
        instance.ratonMoved(c);
        assertEquals(Cursor.DEFAULT_CURSOR, c.getCursor().getType());
        /*
        Otros necesitan gráficos por pantalla
         */
    }

    /**
     * Test of setJpanel method, of class PintaControl.
     */
    @Test
    public void testSetJpanel() {
        System.out.println("setJpanel");
        JPanel mpan = new JPanel();
        assertEquals(0, mpan.getMouseListeners().length);
        assertEquals(0, mpan.getMouseMotionListeners().length);
        JButton botonBorrar = new JButton();
        JToggleButton tb = new JToggleButton();
        PintaControl instance = PintaControl.getInstance(botonBorrar, tb, 1);
        instance.setJpanel(mpan);
        assertEquals(0, mpan.getMouseListeners().length);
        assertEquals(0, mpan.getMouseMotionListeners().length);
        instance.entraModoPintar();
        assertEquals(1, mpan.getMouseListeners().length);
        assertEquals(1, mpan.getMouseMotionListeners().length);
        instance.dejaModoPintar();
        assertEquals(0, mpan.getMouseListeners().length);
        assertEquals(0, mpan.getMouseMotionListeners().length);
    }
}
