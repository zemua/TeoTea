/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author miguel
 */
public class AutoPrinter {

    Component mComp;
    File inputFile;
    File outputFile;

    public AutoPrinter(Component c) {
        mComp = c;
        // Utils para Rotar imagen en caso de que sea más ancha que larga
        String fullPath = System.getProperty("user.home");
        fullPath = fullPath.concat(File.separator).concat("bufferTYT.png");
        inputFile = new File(fullPath);
        inputFile.delete();
    }

    private File bufferImage() {
        CompImager imager = new CompImager(mComp, inputFile);
        imager.createFile();
        return inputFile;
    }

    private boolean clear() {
        return (inputFile.delete());
    }

    public void go() {
        try {
            File f = bufferImage(); //asigna y crea inputfile
            BufferedImage bi = ImageIO.read(f);

            PrinterJob pj = PrinterJob.getPrinterJob();

            pj.setPrintable((Graphics pg, PageFormat pf, int pageNum) -> {
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D g2 = (Graphics2D) pg;
                double x = pf.getImageableX();
                double y = pf.getImageableY();
                g2.translate(x, y); // nos movemos a las coordenadas x y donde empieza la impresión
                //mComp.paint(g2);
                double width = pf.getImageableWidth();
                double height = pf.getImageableHeight();
                double imW = bi.getWidth();
                double imH = bi.getHeight();
                if (width < imW){
                    double scale = imW/width;
                    imW = width;
                    imH = imH/scale;
                }
                if (height < imH){
                    double scale = imH/height;
                    imH = height;
                    imW = imW/scale;
                }
                g2.drawImage(bi, 0, 0, (int)imW, (int)imH, null);
                g2.dispose();
                return Printable.PAGE_EXISTS;
            });

            if (pj.printDialog() == false) {
                return;
            }

            try {
                pj.print();
            } catch (PrinterException e) {
                e.printStackTrace();
                clear(); //borra inputfile
            }
            clear(); //borra inputfile
        } catch (IOException ex) {
            Logger.getLogger(AutoPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
