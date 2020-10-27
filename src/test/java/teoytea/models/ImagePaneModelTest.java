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
import teoytea.views.ImagePane;

/**
 *
 * @author miguel
 */
public class ImagePaneModelTest {
    
    public ImagePaneModelTest() {
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
     * Test of addImage method, of class ImagePaneModel.
     */
    @Test
    public void testAddImage() throws IOException {
        System.out.println("addImage");
        String p = this.getClass().getClassLoader().getResource("teoytea" + File.separator + "resources" + File.separator + "incognito2.png").getPath();
        File f = new File(p);
        BufferedImage img = ImageIO.read(f);
        Point pt = new Point(32, 40);
        ImagePane pane = new ImagePane();
        ImagePaneModel instance = new ImagePaneModel(pane);
        instance.addImage(img, pt);
        assertEquals(1, instance.getImgQty());
        assertEquals(img, instance.getImg(0));
        assertEquals(pt, instance.getPunto(0));
        // Segunda imagen
        String p2 = this.getClass().getClassLoader().getResource("teoytea" + File.separator + "resources" + File.separator + "incognito1.png").getPath();
        File f2 = new File(p2);
        BufferedImage img2 = ImageIO.read(f2);
        Point pt2 = new Point(15, 84);
        instance.addImage(img2, pt2);
        assertEquals(2, instance.getImgQty());
        assertEquals(img2, instance.getImg(1));
        assertEquals(pt2, instance.getPunto(1));
    }    
}
