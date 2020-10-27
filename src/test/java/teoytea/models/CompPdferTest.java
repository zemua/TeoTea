/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.io.File;
import static java.lang.Thread.sleep;
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
public class CompPdferTest {

    public CompPdferTest() {
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
     * Test of convierte method, of class CompPdfer.
     */
    @Test
    public void testConvierte() throws InterruptedException {
        // empezamos probando primero el constructor
        System.out.println("constructor");
        String path = System.getProperty("user.home").concat(File.separator).concat("miPdfTeoTest");
        File file = new File(path);
        File file2 = new File(path.concat(".pdf"));
        JPanel component = new JPanel();
        component.setSize(123, 235);
        CompPdfer instance = new CompPdfer(component, file);
        sleep(50);
        // y pasamos luego a la función "convierte"
        System.out.println("convierte");
        instance.convierte();
        assertTrue(file2.exists());
        if(!file2.delete()){
            fail("no se ha podido borrar el pdf");
        }
        // probando otro que tenga coletilla .pdf
        instance = new CompPdfer(component, file2);
        instance.convierte();
        assertTrue(file2.exists());
        if(!file2.delete()){
            fail("no se ha podido borrar el pdf");
        }
    }

}
