/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;

/**
 *
 * @author miguel
 */
public class CompPrinter {

    /*
    USAMOS AUTOPRINTER EN LUGAR DE ESTE
    */
    
    Component frameToPrint;
    File inputFile;
    DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
    FileInputStream mInput = null;

    public CompPrinter(Component comp){
        frameToPrint = comp;
        String fullPath = System.getProperty("user.home");
        fullPath = fullPath.concat(File.separator).concat("bufferTYT.png");
        inputFile = new File(fullPath);
        inputFile.delete();
    }
    
    private File getImage(){
        CompImager mIm = new CompImager(frameToPrint, inputFile);
        mIm.createFile();
        return inputFile;
    }
    
    private boolean clear() {
        try {
            mInput.close();
        } catch (IOException ex) {
            Logger.getLogger(CompPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (inputFile.delete());
    }
    
    private PrintService getService(){
        PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, getAset());
        System.out.println("número de PrintService[]: " + services.length);
        if (services.length > 0){
            return services[0];
        }
        return null;
    }
    
    private Doc getDoc(){
        Doc doc;
        try{
            mInput = new FileInputStream(inputFile);
            doc = new SimpleDoc(mInput, flavor, null);
        }
        catch (FileNotFoundException e){
            System.out.println("No se ha encontrado file input stream de: " + inputFile.getPath());
            return null;
        }
        return doc;
    }
    
    private PrintRequestAttributeSet getAset() {
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        try{
            BufferedImage bimg = ImageIO.read(inputFile);
            if (bimg.getWidth() > bimg.getHeight()){
                aset.add(OrientationRequested.LANDSCAPE);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }   
        return aset;
    }
    
    public void go(){
        getImage(); // asigna y crea inputFile
        PrintService service = getService();
        if (service != null){
            DocPrintJob pj = service.createPrintJob();
            try{
                pj.print(getDoc(), getAset());
            }
            catch (PrintException e){
                e.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: No se ha encontrado ninguna impresora.");
            System.out.println("No se han encontrado print service compatibles");
        }
        clear();
    }
}
