/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JTextArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.awt.datatransfer.DataFlavor.stringFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
import teoytea.models.DrawPaneTH;
import static teoytea.models.dibujos.BocadilloTH.M_FONT_SIZE;

/**
 *
 * @author miguel
 */
public class BocadilloTHTest {
    
    public BocadilloTHTest() {
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
     * Test of getTextWidth method, of class BocadilloTH.
     */
    @Test
    public void testGetTextWidth() {
        System.out.println("getTextWidth");
        String txt = "";
        int expResult = 0;
        int result = BocadilloTH.getTextWidth(txt);
        assertEquals(expResult, result);
        txt = "hola";
        expResult = 26;
        result = BocadilloTH.getTextWidth(txt);
        assertEquals(expResult, result);
        txt = "WWWW";
        expResult = 48;
        result = BocadilloTH.getTextWidth(txt);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTextHeight method, of class BocadilloTH.
     */
    @Test
    public void testGetTextHeight() {
        System.out.println("getTextHeight");
        int expResult = 15;
        int result = BocadilloTH.getTextHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFont method, of class BocadilloTH.
     */
    @Test
    public void testGetFont() {
        System.out.println("getFont");
        Font expResult = new Font("TimesRoman", Font.PLAIN, M_FONT_SIZE);
        Font result = BocadilloTH.getFont();
        assertEquals(expResult, result);
    }

    /**
     * Test of createTransferable method, of class BocadilloTH.
     */
    @Test
    public void testCreateTransferable() throws UnsupportedFlavorException, IOException {
        System.out.println("createTransferable");
        BocadilloTH instance = new BocadilloTH();
        
        JTextArea comp = new JTextArea();
        comp.setText("texto de prueba");
        Transferable trs = instance.createTransferable(comp);
        assertTrue(trs.isDataFlavorSupported(imageFlavor));
        assertTrue(!trs.isDataFlavorSupported(stringFlavor));
        assertEquals(JLabel.class, trs.getTransferData(imageFlavor).getClass());
        assertEquals(null, trs.getTransferData(stringFlavor));
        JLabel jl0 = (JLabel) trs.getTransferData(imageFlavor);
        assertEquals(111, jl0.getIcon().getIconWidth());
        assertEquals(38, jl0.getIcon().getIconHeight());
        
        comp.setText("");
        assertEquals(null, trs.getTransferData(imageFlavor));
        
        JLabel comp2 = new JLabel();
        Transferable trs2 = instance.createTransferable(comp2);
        assertEquals(null, trs2.getTransferData(imageFlavor));
        DataFlavor[] flvr = {imageFlavor};
        assertEquals(flvr.length, trs.getTransferDataFlavors().length);
        assertEquals(1, trs.getTransferDataFlavors().length);
        assertEquals(flvr[0], trs.getTransferDataFlavors()[0]);
        
        JTextArea comp3 = new JTextArea();
        comp3.setText("probanding");
        BocadilloTH.setTipo(DrawPaneTH.TipoLabel.Nube);
        Transferable trs3 = instance.createTransferable(comp3);
        assertTrue(trs3.isDataFlavorSupported(imageFlavor));
        assertTrue(!trs3.isDataFlavorSupported(stringFlavor));
        assertEquals(JLabel.class, trs3.getTransferData(imageFlavor).getClass());
        JLabel jl3 = (JLabel) trs3.getTransferData(imageFlavor);
        assertEquals(105, jl3.getIcon().getIconWidth());
        assertEquals(66, jl3.getIcon().getIconHeight());
        assertEquals(null, trs.getTransferData(stringFlavor));
    }

    /**
     * Test of exportDone method, of class BocadilloTH.
     */
    @Test
    public void testExportDone() {
        System.out.println("exportDone");
        JTextArea source = new JTextArea();
        source.setText("texto");
        Transferable data = null;
        int action = TransferHandler.MOVE;
        BocadilloTH instance = new BocadilloTH();
        instance.exportDone(source, data, action);
        assertEquals("", source.getText());
        source.setText("texto");
        action = TransferHandler.COPY;
        instance.exportDone(source, data, action);
        assertEquals("texto", source.getText());
        action = TransferHandler.NONE;
        instance.exportDone(source, data, action);
        assertEquals("texto", source.getText());
    }

    /**
     * Test of getSourceActions method, of class BocadilloTH.
     */
    @Test
    public void testGetSourceActions() {
        System.out.println("getSourceActions");
        JLabel jl = new JLabel();
        jl.setText("jur");
        JTextArea jta = new JTextArea();
        jta.setText("jur");
        BocadilloTH instance = new BocadilloTH();
        int expResult = TransferHandler.NONE;
        int result = instance.getSourceActions(jl);
        assertEquals(expResult, result);
        result = instance.getSourceActions(jta);
        expResult = TransferHandler.MOVE;
        assertEquals(expResult, result);
        jta.setText("");
        result = instance.getSourceActions(jta);
        expResult = TransferHandler.NONE;
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dataToLabel method, of class BocadilloTH.
     */
    @Test
    public void testDataToLabel(){
        BocadilloTH handler = new BocadilloTH();
        
        int id = 34;
        DrawPaneTH.TipoLabel tipo = DrawPaneTH.TipoLabel.Bocadillo;
        DrawPaneTH.TipoLabel tipo2 = DrawPaneTH.TipoLabel.Nube;
        String[] mtexto = {"una linea", "dos lineas"};
        int dbx = 45;
        int dby = 56;
        ColorEnum.Cenum color = ColorEnum.Cenum.AZUL;
        
        JLabel label = handler.dataToLabel(id, tipo, mtexto, dbx, dby, color);
        assertEquals(id, Integer.parseInt(label.getName()));
        Rectangle r = label.getBounds();
        
        assertEquals(dbx, r.x);
        assertEquals(dby, r.y);
        assertEquals(74, label.getWidth());
        assertEquals(53, label.getHeight());
        
        JLabel label2 = handler.dataToLabel(id, tipo2, mtexto, dbx, dby, color);
        assertEquals(94, label2.getWidth());
        assertEquals(81, label2.getHeight());
    }
    
}
