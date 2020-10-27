/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.views;

import java.awt.Color;
import javax.swing.JList;
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
public class ColorCellRendererTest {
    
    public ColorCellRendererTest() {
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
     * Test of getListCellRendererComponent method, of class ColorCellRenderer.
     */
    @Test
    public void testGetListCellRendererComponent() {
        System.out.println("getListCellRendererComponent");
        JList list = new JList();
        String value = "";
        int index = 0;
        boolean isSelected = false;
        boolean cellHasFocus = false;
        ColorCellRenderer instance = new ColorCellRenderer();
        // default con value = ""
        Color expResult = Color.BLACK;
        Color result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
        // rojo
        value = "Rojo";
        expResult = Color.RED;
        result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
        // verde
        value = "Verde";
        expResult = Color.GREEN;
        result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
        // amarillo
        value = "Amarillo";
        expResult = Color.YELLOW;
        result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
        // naranja
        value = "Naranja";
        expResult = Color.ORANGE;
        result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
        // azul
        value = "Azul";
        expResult = Color.CYAN;
        result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
        // negro
        value = "Negro";
        expResult = Color.BLACK;
        result = instance.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).getBackground();
        assertEquals(expResult, result);
    }
    
}
