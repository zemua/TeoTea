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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;

/**
 *
 * @author miguel
 */
public class CompPdfer {

    Component mComp;
    File inputFile;
    File outputFile;
    DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
    FileOutputStream fos = null;
    FileInputStream fis = null;
    StreamPrintService mServ = null;
    StreamPrintService psService = null;

    public CompPdfer(Component comp, File ofile) {
        mComp = comp;
        outputFile = ofile;
        String ruta = ofile.getPath();
        if (!ruta.endsWith(".pdf")){
            ruta = ruta.concat(".pdf");
            outputFile = new File(ruta);
        }

        String fullPath = System.getProperty("user.home").concat(File.separator).concat("bufferTYT.png");
        inputFile = new File(fullPath);
        inputFile.delete(); // por si existía antes
    }

    private File bufferImage() {
        CompImager imager = new CompImager(mComp, inputFile);
        imager.createFile();
        return inputFile;
    }

    private boolean clear() {
        try {
            fos.close();
            fis.close();
            mServ.dispose();
            psService.dispose();
        } catch (IOException ex) {
            Logger.getLogger(CompPdfer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (inputFile.delete());
    }

    private StreamPrintService getService() {
        String psMimetype = DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType();
        StreamPrintServiceFactory[] psfactories = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(flavor, psMimetype);
        System.out.println("stream print service factory psfactories[] qty: " + psfactories.length);
        outputFile.delete();
        System.out.println("outputFile: " + outputFile);
        try {
            outputFile.createNewFile();
            fos = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            System.out.println("archivo output no encontrado");
        } catch (IOException e) {
            System.out.println("error creando outputFile");
            e.printStackTrace();
        }
        if (fos == null) {
            return null;
        }
        if (psfactories.length >0){
            psService = psfactories[0].getPrintService(fos);
        }
        System.out.println("el stream print service es: " + psService);
        return psService;
    }
    
    public void closeFos(){
        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(CompPdfer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Doc getDoc() {
        Doc doc;
        try {
            fis = new FileInputStream(inputFile);
            doc = new SimpleDoc(fis, flavor, null);
        } catch (FileNotFoundException e) {
            System.out.println("no se ha encontrado la inputFile");
            return null;
        }
        return doc;
    }
    
    public void closeFis(){
        try {
            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(CompPdfer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        PrinterResolution pr = new PrinterResolution(300, 300, PrinterResolution.DPI);
        aset.add(pr);
        return aset;
    }

    public void convierte() {
        bufferImage(); //asigna y crea inputfile
        mServ = getService();
        if (mServ != null) {
            DocPrintJob pj = mServ.createPrintJob();
            try {
                pj.print(getDoc(), getAset());
            } catch (PrintException e) {
                System.out.println("print exception");
            }
        }
        else {
            System.out.println("no se ha encontrado print service compatible");
        }
        clear();
    }
}
