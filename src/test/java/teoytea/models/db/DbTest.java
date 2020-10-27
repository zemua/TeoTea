/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.sql.Connection;
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
public class DbTest {

    public DbTest() {
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
     * Test of getInstance method, of class Db.
     */
    @Test
    public void testGetInstance() {
        Db instance = Db.getInstance();
        Db instance2 = Db.getInstance();
        assertEquals(instance, instance2);
        assertTrue(instance == instance2);
    }

    /**
     * Test of getConnection method, of class Db.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Db instance = Db.getInstance();
        Connection con = instance.getConnection();
        assertTrue(!(con == null));
    }
}
