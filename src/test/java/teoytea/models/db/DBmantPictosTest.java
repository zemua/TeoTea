/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DBmantPictosTest {
    
    public DBmantPictosTest() {
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
     * Test of descargaFaltantes method, of class DBmantPictos.
     */
    @Test
    public void testDescargaFaltantes() {
        try {
            System.out.println("descargaFaltantes");
            DBPictogramas pct = new DBPictogramas();
            long idp = pct.anhadirPictoToComp(0, 5422, 10, 20, 30, 40);
            File f = new File(ArchivosModel.getResetedPath().concat(File.separator + "arasaac" + File.separator + "5422.png"));
            f.delete();
            assertTrue(!f.exists());
            DBmantPictos instance = new DBmantPictos();
            instance.descargaFaltantes();
            sleep(5000);
            assertTrue(f.exists());
            pct.borrarPictoDeComp((int)idp);
        } catch (InterruptedException ex) {
            Logger.getLogger(DBmantPictosTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
