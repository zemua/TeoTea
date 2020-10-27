/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.controllers;

import java.awt.Component;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import static org.junit.jupiter.api.Assertions.*;
import teoytea.models.ArchivosModel;
import teoytea.views.DibujarView;
import teoytea.views.GaleriaView;
import teoytea.views.MyImgListRender;

/**
 *
 * @author miguel
 */
public class PictoControllerTest {

    public PictoControllerTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of getAm method, of class PictoController.
     */
    @org.junit.jupiter.api.Test
    public void testGetAm() {
        System.out.println("getAm");
        PictoController instance = new PictoController();
        ArchivosModel expResult = new ArchivosModel();
        ArchivosModel result = instance.getAm();
        assertEquals(expResult.getClass(), result.getClass());
    }

    /**
     * Test of eventoBoton method, of class PictoController.
     */
    @org.junit.jupiter.api.Test
    public void testEventoBoton() {
        System.out.println("eventoBoton");
        PictoController instance = new PictoController();
        PictoController.Accion mBoton;
        PictoController.Accion expResult;
        PictoController.Accion result;
        // caso 1
        mBoton = PictoController.Accion.Add;
        expResult = PictoController.Accion.Add;
        result = instance.eventoBoton(mBoton, 0);
        assertEquals(expResult, result);
        assertEquals(instance.getFrame().getContentPane().getClass(), DibujarView.class);
        assertEquals(instance.getPanel().getClass(), DibujarView.class);
        // caso 2
        mBoton = PictoController.Accion.Guarda;
        expResult = PictoController.Accion.Guarda;
        result = instance.eventoBoton(mBoton, 0);
        assertEquals(expResult, result);
        assertEquals(instance.getFrame().getContentPane().getClass(), GaleriaView.class);
        assertEquals(instance.getPanel().getClass(), GaleriaView.class);
        // caso 3
        mBoton = PictoController.Accion.Open;
        expResult = PictoController.Accion.Open;
        result = instance.eventoBoton(mBoton, 0);
        assertEquals(expResult, result);
        assertEquals(instance.getFrame().getContentPane().getClass(), DibujarView.class);
        assertEquals(instance.getPanel().getClass(), DibujarView.class);
    }

    /**
     * Test of ponerRenderer method, of class PictoController.
     */
    @org.junit.jupiter.api.Test
    public void testPonerRenderer() {
        System.out.println("ponerRenderer");
        JList c = new JList();
        c.setModel(new DefaultListModel());
        PictoController instance = new PictoController();
        instance.ponerRenderer(c);
        assertEquals(c.getCellRenderer().getClass(), MyImgListRender.class);
    }
    
    @org.junit.jupiter.api.Test
    public void testGetBorrante(){
        DibujarView dv = new DibujarView(new PictoController(), 1);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        dv.add(jp1);
        jp1.add(jp2);
        Component comp = PictoController.getBorrante(jp2);
        assertTrue(comp.equals(dv));
    }
    
    @org.junit.jupiter.api.Test
    public void testGetTextoDropable(){
        DibujarView dv = new DibujarView(new PictoController(), 1);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        dv.add(jp1);
        jp1.add(jp2);
        Component comp = PictoController.getTextoDropable(jp2);
        assertTrue(comp.equals(dv));
    }
    
    @org.junit.jupiter.api.Test
    public void testGetContenetor(){
        DibujarView dv = new DibujarView(new PictoController(), 1);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        dv.add(jp1);
        jp1.add(jp2);
        Component comp = PictoController.getContenetor(jp2);
        assertTrue(comp.equals(dv));
    }

}
