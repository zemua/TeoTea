/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
public class CompImagerTest {
    
    public CompImagerTest() {
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
     * Test of createFile method, of class CompImager.
     */
    @Test
    public void testCompImagerCompleto() {
        System.out.println("compImager completo");
        String path = System.getProperty("user.home").concat(File.separator).concat("miImagenTeoTest");
        File file = new File(path);
        JPanel component = new JPanel();
        component.setSize(123, 235);
        CompImager instance = new CompImager(component, file);
        instance.createFilePeque();
        
        String path2 = path.concat(".png");
        File file2 = new File(path2);
        assertTrue(file2.exists());
        try {
            BufferedImage bi =ImageIO.read(file2);
            assertEquals(235, bi.getHeight());
            assertEquals(123, bi.getWidth());
        } catch (IOException ex) {
            fail("no se puede leer la imagen creada");
            Logger.getLogger(CompImagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!file2.delete()){
            fail("no se ha podido borrar la imagen");
        }
        instance = new CompImager(component, file2);
        instance.createFilePeque();
        assertTrue(file2.exists());
        if(!file2.delete()){
            fail("no se ha podido borrar la imagen");
        }
        
        // el de aumento
        path = System.getProperty("user.home").concat(File.separator).concat("miImagenTeoTest");
        file = new File(path);
        component = new JPanel();
        component.setSize(123, 235);
        instance = new CompImager(component, file);
        instance.createFile();
        
        path2 = path.concat(".png");
        file2 = new File(path2);
        assertTrue(file2.exists());
        try {
            BufferedImage bi =ImageIO.read(file2);
            assertEquals(2350, bi.getHeight());
            assertEquals(1230, bi.getWidth());
        } catch (IOException ex) {
            fail("no se puede leer la imagen creada");
            Logger.getLogger(CompImagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!file2.delete()){
            fail("no se ha podido borrar la imagen");
        }
        instance = new CompImager(component, file2);
        instance.createFile();
        assertTrue(file2.exists());
        if(!file2.delete()){
            fail("no se ha podido borrar la imagen");
        }
    }
    
}
