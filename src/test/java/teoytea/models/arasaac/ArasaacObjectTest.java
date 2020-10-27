/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

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
public class ArasaacObjectTest {
    
    public ArasaacObjectTest() {
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
     * Test of getId method, of class ArasaacObject.
     */
    @Test
    public void testSetGetId() {
        System.out.println("set y get Id");
        ArasaacObject instance = new ArasaacObject();
        instance.setId(1);
        int i = instance.getId();
        assertEquals(1, i);
        i=5;
        instance.setId(5);
        assertEquals(5, i);
    }
}
