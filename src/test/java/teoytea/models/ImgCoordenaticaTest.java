/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
public class ImgCoordenaticaTest {
    
    public ImgCoordenaticaTest() {
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
     * Test of setImage method, of class ImgCoordenatica.
     */
    @Test
    public void testSetGetImage() throws IOException {
        System.out.println("setGetImage");
        String p = this.getClass().getClassLoader().getResource("teoytea" + File.separator + "resources" + File.separator + "incognito2.png").getPath();
        File f = new File(p);
        BufferedImage img = ImageIO.read(f);
        Point pt = new Point(32, 40);
        ImgCoordenatica instance = new ImgCoordenatica(img, pt);
        instance.setImage(img);
        assertEquals(img, instance.getImage());
    }

    /**
     * Test of setPoint method, of class ImgCoordenatica.
     */
    @Test
    public void testSetGetPoint() throws IOException {
        System.out.println("setGetPoint");
        String p = this.getClass().getClassLoader().getResource("teoytea" + File.separator + "resources" + File.separator + "incognito2.png").getPath();
        File f = new File(p);
        BufferedImage img = ImageIO.read(f);
        Point pt = new Point(32, 40);
        ImgCoordenatica instance = new ImgCoordenatica(img, pt);
        instance.setPoint(pt);
        assertEquals(pt, instance.getPoint());
    }
}
