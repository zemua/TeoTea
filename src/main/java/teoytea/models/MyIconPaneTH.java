/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.TransferHandler;
import static teoytea.models.DragImageList.imageFiles;
import teoytea.models.arasaac.ArasaacTH;
import static teoytea.models.arasaac.ArasaacTH.dragIndex;
import teoytea.models.arasaac.ArchivosArasaac;

/**
 *
 * @author miguel
 */

public class MyIconPaneTH extends TransferHandler {
    
    JList mList;
    DefaultListModel mModel;
    private static final DataFlavor[] FLAVORS = {imageFlavor};
    
    public MyIconPaneTH(JList list){
        super();
        mList = list;
        mModel = (DefaultListModel) list.getModel();
    }
    
    @Override
    public int getSourceActions(JComponent c) {
        //System.out.println("get source actions de MyIconPane");
        return TransferHandler.COPY_OR_MOVE;
    }
    
    @Override
    public boolean canImport(TransferSupport support) {
        //System.out.println("can MyIconPane import?");
        if (support.isDataFlavorSupported(FLAVORS[0])){
            //System.out.println("yes it can");
            return true;
        }
        //System.out.println("no it cannot");
        return false;
    }
    
    @Override
    public boolean importData(TransferSupport support) {

        System.out.println("Importando datos en MyIconPane");
        String id = "";
        try {
            id = (((JLabel)support.getTransferable().getTransferData(DataFlavor.imageFlavor)).getName());
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(MyIconPaneTH.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyIconPaneTH.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!canImport(support) || (DragImageList.inDrag != true) && (ArasaacTH.getDragging() != true)) {
            return false;
        }

        //Si la imagen viene del Arasaac...
        if (ArasaacTH.getDragging() == true && DragImageList.inDrag == false) {
            try {
                mModel.add(mModel.getSize(), support.getTransferable().getTransferData(DataFlavor.imageFlavor));
                /*
                Guardar la imagen original del arasaac a los recursos del jar
                 */
                ImageIcon originalIcon = new ImageIcon(ArchivosArasaac.getInstance().getLocalImage(Integer.parseInt(id)));
                BufferedImage bI = new BufferedImage(originalIcon.getIconWidth(), originalIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics g = bI.createGraphics();
                originalIcon.paintIcon(null, g, 0, 0);
                g.dispose();
                /*
                consigue un nombre de archivo que no exista
                 */
                String name = "";
                Integer i = 0;
                File f = null;
                while (name.equals("")) {
                    URL url = this.getClass().getClassLoader().getResource("");
                    f = new File(url.getPath().concat(i.toString()).concat(".png"));
                    if (!f.exists()) {
                        name = i.toString();
                    }
                    i++;
                }
                /*
                Y guarda la imagen en ese nombre de archivo
                 */
                try {
                    imageFiles.add(mModel.getSize(), f.getPath()); // y lo añadimos a la List de Paths de archivos
                    ImageIO.write(bI, "png", f);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                /*
                Guardada la imagen
                 */
                System.out.println("añadida imagen en " + dragIndex + " desde Arasaac");
                return true;
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(DragImageList.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                Logger.getLogger(DragImageList.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        // I ahora si es arrastrando un label para reordenarlo
        try {
            String dragUrl = imageFiles.get(DragImageList.dragIndex);
            mModel.removeElement(DragImageList.draggedIm);
            imageFiles.remove(DragImageList.dragIndex);
            mModel.add(mModel.getSize(), DragImageList.draggedIm);
            imageFiles.add(mModel.getSize(), dragUrl);
            System.out.println("de " + dragIndex + " movido a " + mModel.getSize());
            System.out.println("el indice de imagen-archivo queda: " + imageFiles.toString());
            DragImageList.dragIndex = -1;
            return true;
        } catch (Exception e) {
            DragImageList.dragIndex = -1;
            return false;
        }
    }
}
