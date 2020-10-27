/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import teoytea.models.ArchivosModel;

/**
 *
 * @author miguel
 */
public class ArchivosComposicion {

    String base;

    public ArchivosComposicion(String basePath) {
        base = basePath;
        if (!base.endsWith(File.separator)) {
            base = base.concat(File.separator);
        }
    }

    public ArchivosComposicion() {
        base = ArchivosModel.getResetedPath();
        if (!base.endsWith(File.separator)) {
            base = base.concat(File.separator);
        }
        createFolder();
    }

    public String getComposicionesPath(String basePath) {
        String rootPath = basePath;
        if (!basePath.endsWith(File.separator)) {
            rootPath = basePath.concat(File.separator);
        }
        rootPath = rootPath.concat("composiciones" + File.separator);
        return rootPath;
    }

    public File getComposicionFile(int id) {
        File file = new File(getComposicionesPath(base).concat(Integer.toString(id).concat(".png")));
        return file;
    }

    public ImageIcon getComposicionIcon(int id) {
        ImageIcon ic = null;
        try {
            File file = getComposicionFile(id);
            BufferedImage bi = ImageIO.read(file);
            ic = new ImageIcon(bi);
        } catch (IOException ex) {
            Logger.getLogger(ArchivosComposicion.class.getName()).log(Level.SEVERE, null, ex);
            ic = new ImageIcon(getClass().getResource("/teoytea/resources/aux/cuadro.png"));
        }
        return ic;
    }

    final void createFolder() {
        File f = new File(getComposicionesPath(base));
        if (!f.exists() || !f.isDirectory()) {
            f.mkdir();
        }
    }

    public boolean borraImagen(int id) {
        File file = getComposicionFile(id);
        return file.delete();
    }
}
