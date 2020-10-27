/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import teoytea.models.ArchivosModel;

/**
 *
 * @author miguel
 */
public class ArchivosArasaacTest {
    
    public ArchivosArasaacTest() {
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
     * Test of getInstance method, of class ArchivosArasaac.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ArchivosArasaac ins1 = ArchivosArasaac.getInstance();
        ArchivosArasaac ins2 = ArchivosArasaac.getInstance();
        assertEquals(ins1, ins2);
    }

    /**
     * Test of localExists method, of class ArchivosArasaac.
     */
    @Test
    public void testLocalExists_ArrayList_String() {
        // junto con la siguiente
    }

    /**
     * Test of localExists method, of class ArchivosArasaac.
     */
    @Test
    public void testLocalExists_GenericType() {
        ArchivosArasaac inst = ArchivosArasaac.getInstance();
        String name = "5421";
        assertTrue(inst.localExists(name));
        int nombre = 5421;
        assertTrue(inst.localExists(nombre));
        name = "78965412302589aaa";
        assertTrue(!inst.localExists(name));
        nombre = 789654123;
        assertTrue(!inst.localExists(nombre));
    }

    /**
     * Test of leerImagen method, of class ArchivosArasaac.
     */
    @Test
    public void testLeerImagen() {
        File f = new File(ArchivosModel.getResetedPath().concat(File.separator + "arasaac").concat(File.separator + "5421.png"));
        ArchivosArasaac instance = ArchivosArasaac.getInstance();
        BufferedImage img = instance.leerImagen(f);
        assertTrue(img.getHeight() > 10);
    }

    /**
     * Test of getLocalImage method, of class ArchivosArasaac.
     */
    @Test
    public void testGetLocalImage() {
        ArchivosArasaac inst = ArchivosArasaac.getInstance();
        BufferedImage img = inst.getLocalImage(5421);
        assertTrue(img.getHeight() > 10);
    }

    /**
     * Test of saveImage method, of class ArchivosArasaac.
     */
    @Test
    public void testSaveImage() {
        ArchivosArasaac inst = ArchivosArasaac.getInstance();
        File saved = new File(ArchivosModel.getResetedPath().concat(File.separator + "arasaac").concat(File.separator + "probandinggg.png"));
        saved.delete();
        BufferedImage img = inst.getLocalImage(5421);
        assertTrue(inst.saveImage(img, "probandinggg"));
        assertTrue(!inst.saveImage(img, "probandinggg"));
        assertTrue(saved.delete());
    }
    
}
