/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import teoytea.models.ArchivosModel;

/**
 *
 * @author miguel
 */
public class ArchivosArasaac {

    private String rootPath;
    private static ArchivosArasaac aa = null;

    private ArchivosArasaac() {
        getArasaacPath();
    }

    public static ArchivosArasaac getInstance() {
        if (aa == null) {
            aa = new ArchivosArasaac();
        }
        return aa;
    }

    private final String getArasaacPath() {
        rootPath = ArchivosModel.getResetedPath().concat(File.separator + "arasaac");
        return rootPath;
    }
    
    private final String getFlechasPath(){
        rootPath = ArchivosModel.getResetedPath().concat(File.separator + "flechas");
        return rootPath;
    }
    
    private final String getBocadillosPath(){
        rootPath = ArchivosModel.getResetedPath().concat(File.separator + "bocadillos");
        return rootPath;
    }
    
    private final String getNubesPath(){
        rootPath = ArchivosModel.getResetedPath().concat(File.separator + "nubes");
        return rootPath;
    }

    private ArrayList<Path> getNamesList() {
        return ArchivosModel.getPathOfImagesOnly(rootPath);
    }

    public boolean localExists(ArrayList<Path> namesList, String searchName) {
        String name = stringToPng(searchName);
        for (int i = 0; i < namesList.size(); i++) {
            String thisName = namesList.get(i).getFileName().toString();
            if (thisName.equals(name)) {
                //System.out.println("ya está en local");
                return true;
            }
        }
        //System.out.println("no está en local");
        return false;
    }

    public <T> boolean localExists(T searchName) {
        return localExists(getNamesList(), searchName.toString());
    }

    public BufferedImage leerImagen(File f) {
        BufferedImage img = null;
        try {
            if (ArchivosModel.isImage(Files.probeContentType(f.toPath()))) {
                try {
                    img = ImageIO.read(f);
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("Tratando de buffear archivo que no es imagen!");
            }
        } catch (IOException ex) {
            Logger.getLogger(ArchivosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

    public <T> BufferedImage getLocalImage(T n) {
        File f = new File(this.<T>idToUri(n).getPath());
        BufferedImage buffer = leerImagen(f);
        return buffer;
    }

    private <T> String stringToPng(T s) {
        String spng;
        if (!s.toString().endsWith(".png")) {
            spng = s.toString().concat(".png");
        } else {
            spng = s.toString();
        }
        return spng;
    }

    private<T> URI idToUri(T n) {
        URI uri = URI.create(rootPath.concat("/" + n.toString() + ".png"));
        return uri;
    }

    public boolean saveImage(BufferedImage arasaacImage, String saveName) {
        String mSave = this.<String>stringToPng(saveName);
        if (localExists(mSave)) {
            return false;
        } else {
            String spath = rootPath.concat("/" + mSave);
            Path mPath = Paths.get(spath);
            return ArchivosModel.anadeArchivo(mPath, arasaacImage);
        }
    }
}
