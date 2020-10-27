/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
public class ArchivosComposicionTest {
    
    public ArchivosComposicionTest() {
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
     * Test of getComposicionesPath method, of class ArchivosComposicion.
     */
    @Test
    public void testGetComposicionesPath() {
        System.out.println("getComposicionesPath");
        String basePath = "base";
        ArchivosComposicion instance = new ArchivosComposicion();
        String expResult = "base" + File.separator + "composiciones" + File.separator;
        String result = instance.getComposicionesPath(basePath);
        assertEquals(expResult, result);
        basePath = "base/";
        result = instance.getComposicionesPath(basePath);
        assertEquals(expResult, result);
    }

    /**
     * Test of getComposicionFile method, of class ArchivosComposicion.
     */
    @Test
    public void testGetComposicionFile() {
        try {
            System.out.println("getComposicionFile");
            String base = ArchivosModel.getResetedPath();
            ArchivosComposicion instance = new ArchivosComposicion();
            File result = instance.getComposicionFile(0);
            String expresult = ArchivosModel.getResetedPath().concat(File.separator + "composiciones" + File.separator + "0.png");
            assertEquals(expresult, result.getAbsolutePath());
            
            //testGetComposicionFile
            BufferedImage bi = new BufferedImage(10, 10, 10);
            ImageIO.write(bi, "png", result);
            ImageIcon icon = instance.getComposicionIcon(0);
            assertTrue(icon != null);
            
            // clear
            result.delete();
            assertTrue(!result.exists());
        } catch (IOException ex) {
            Logger.getLogger(ArchivosComposicionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getComposicionIcon method, of class ArchivosComposicion.
     */
    @Test
    public void testGetComposicionIcon() {
        System.out.println("getComposicionIcon");
        // se hace en testGetComposicionFile
    }

    /**
     * Test of createFolder method, of class ArchivosComposicion.
     */
    @Test
    public void testCreateFolder() {
        System.out.println("createFolder");
        ArchivosComposicion instance = new ArchivosComposicion(ArchivosModel.getResetedPath().concat(File.separator + "teoyteatesting" + File.separator));
        
        File root = new File(ArchivosModel.getResetedPath().concat(File.separator + "teoyteatesting" + File.separator));
        File f = new File(instance.getComposicionesPath(ArchivosModel.getResetedPath().concat(File.separator + "teoyteatesting" + File.separator)));
        root.mkdir();
        assertTrue(root.exists());
        assertTrue(!f.exists());
        
        instance.createFolder();
        assertTrue(f.exists());
        
        f.delete();
        assertTrue(!f.exists());
        root.delete();
        assertTrue(!root.exists());
    }

    /**
     * Test of borraImagen method, of class ArchivosComposicion.
     */
    @Test
    public void testBorraImagen() {
        try {
            System.out.println("borraImagen");
            int id = 0;
            ArchivosComposicion instance = new ArchivosComposicion();
            File f = instance.getComposicionFile(id);
            f.createNewFile();
            assertTrue(f.exists());
            assertTrue(instance.borraImagen(id));
            assertTrue(!f.exists());
        } catch (IOException ex) {
            Logger.getLogger(ArchivosComposicionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
