/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
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
public class ArchivosModelTest {

    public ArchivosModelTest() {
    }

    ArchivosModel am = new ArchivosModel();
    String mPath = "/teoytea/test/resources/archivotest.png";
    String archivoTest = "/teoytea/test/resources/test1.png";

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        String p = am.getClass().getClassLoader().getResource("teoytea" + File.separator + "resources" + File.separator).getPath();
        System.out.println(p);
        am.setRootPath(p);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of anadeArchivo method, of class ArchivosModel.
     */
    @Test
    public void testAnadeArchivo() {
        System.out.println("anadeArchivo");
        Path path = Paths.get(am.getRootPath() + File.separator + "archivotest.png");
        File f = new File(am.getRootPath() + File.separator + "archivotest.png");
        if (f.exists()) {
            fail("el archivo ya existe antes de crearlo");
        }
        BufferedImage buffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        am.anadeArchivo(path, buffer);
        assertTrue(f.exists());
    }

    /**
     * Test of borraArchivo method, of class ArchivosModel.
     */
    @Test
    public void testBorraArchivo() {
        System.out.println("borraArchivo");
        Path path = Paths.get(am.getRootPath() + File.separator + "archivotest.png");
        File f = new File(am.getRootPath() + File.separator + "archivotest.png");
        if (!f.exists()) {
            fail("el archivo todavía no está creado");
        }
        am.borraArchivo(path);
        assertTrue(!f.exists());
    }

    /**
     * Test of setGetRootPath method, of class ArchivosModel.
     */
    @Test
    public void testSetGetRootPath() {
        System.out.println("setGetRootPath");
        String path = "este es el path";
        am.setRootPath(path);
        assertEquals(am.getRootPath(), path);
    }

    /**
     * Test of resetRootPath method, of class ArchivosModel.
     */
    @Test
    public void testResetRootPath() {
        System.out.println("resetRootPath");
        String expResult = System.getProperty("user.home").concat(File.separator).concat("TEOyTEAimagenes");
        String result = am.resetRootPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWidth method, of class ArchivosModel.
     */
    @Test
    public void testSetGetWidth() {
        System.out.println("setWidth");
        int i = 84;
        am.setWidth(i);
        assertEquals(i, am.getWidth());
    }

    /**
     * Test of getImagePathMap method, of class ArchivosModel.
     */
    @Test
    public void testGetImagePathMap() {
        System.out.println("getImagePathMap");
        Map<String, ImageIcon> result = am.getImagePathMap();
        assertEquals(9, result.size());
        Set<String> mKey = result.keySet();
        String i1 = "caja";
        String i2 = "incognito2";
        assertTrue(mKey.contains(i1));
        assertTrue(mKey.contains(i2));
        assertEquals(result.get(i1).getClass(), ImageIcon.class);
        assertTrue(result.get(i1) != null);
        assertEquals(result.get(i2).getClass(), ImageIcon.class);
        assertTrue(result.get(i2) != null);
    }

    /**
     * Test of scaleImage method, of class ArchivosModel.
     */
    @Test
    public void testScaleImage_3args() {
        System.out.println("scaleImage 3 args");
        File f = new File(am.getRootPath() + File.separator + "incognito1.png");
        assertTrue(f.exists());
        BufferedImage bi = am.leerImagen(f);
        ImageIcon ic = new ImageIcon(bi);
        ImageIcon result = ArchivosModel.scaleImage(ic, 73, 82);
        assertEquals(result.getIconHeight(), 73);
        assertEquals(result.getIconWidth(), 73);
    }

    /**
     * Test of scaleImage method, of class ArchivosModel.
     */
    @Test
    public void testScaleImage_ImageIcon_int() {
        System.out.println("scaleImage 2 args");
        File f = new File(am.getRootPath() + File.separator + "incognito1.png");
        assertTrue(f.exists());
        BufferedImage bi = am.leerImagen(f);
        ImageIcon ic = new ImageIcon(bi);
        ImageIcon result = ArchivosModel.scaleImage(ic, 82);
        assertEquals(result.getIconHeight(), 82);
        assertEquals(result.getIconWidth(), 82);
    }

}
