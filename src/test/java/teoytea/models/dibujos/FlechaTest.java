/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
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
public class FlechaTest {
    
    public FlechaTest() {
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
     * Test of createArrowShape method, of class Flecha.
     */
    @Test
    public void testCreateArrowShape() {
        System.out.println("createArrowShape");
        Point fromPt = new Point(25, 30);
        Point toPt = new Point(50, 55);
        Polygon pol = new Polygon();
        pol.addPoint(22, 27);
        pol.addPoint(50, 27);
        pol.addPoint(50, 55);
        pol.addPoint(22, 55);
        Shape expResult = pol;
        Shape result = Flecha.createArrowShape(fromPt, toPt);
        assertEquals(expResult.getBounds(), result.getBounds());
        // otro
        fromPt = new Point(25, 30);
        toPt = new Point(50, 50);
        pol = new Polygon();
        pol.addPoint(22, 27);
        pol.addPoint(50, 27);
        pol.addPoint(50, 52);
        pol.addPoint(22, 52);
        expResult = pol;
        result = Flecha.createArrowShape(fromPt, toPt);
        assertEquals(expResult.getBounds(), result.getBounds());
        // otro
        fromPt = new Point(25, 25);
        toPt = new Point(25, 60);
        pol = new Polygon();
        pol.addPoint(16, 24);
        pol.addPoint(34, 24);
        pol.addPoint(34, 60);
        pol.addPoint(16, 60);
        expResult = pol;
        result = Flecha.createArrowShape(fromPt, toPt);
        assertEquals(expResult.getBounds(), result.getBounds());
    }
    
}
