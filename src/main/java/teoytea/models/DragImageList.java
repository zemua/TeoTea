/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.TransferHandler;
import teoytea.interfaces.ControladorInterface;
import teoytea.models.arasaac.ArasaacTH;
import teoytea.models.arasaac.ArchivosArasaac;

/**
 *
 * @author miguel
 */
public class DragImageList extends TransferHandler implements Transferable {

    public static boolean DRAGGING_THIS = false;
    public static boolean DROP_EN_BORRAR = false;
    private static final DataFlavor[] FLAVORS = {imageFlavor};
    public static ArrayList<String> imageFiles = new ArrayList<>();
    private final JList previewList;
    public static boolean inDrag;
    private final DefaultListModel model;
    public static int dragIndex = -1;
    public static Object draggedIm;
    private JButton mDel;
    private ControladorInterface controller;

    public DragImageList(JList mList, DefaultListModel mModel, JButton del, ControladorInterface mC) {
        super();
        previewList = mList;
        model = mModel;
        mDel = del;
        controller = mC;
    }

    @Override
    public int getSourceActions(JComponent c) {
        System.out.println("get source actions de Lista");
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        System.out.println("can Lista import?");

        if (!support.isDataFlavorSupported(FLAVORS[0])) {
            return false;
        }
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        if (dl.getIndex() == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean importData(TransferSupport support) {

        System.out.println("Importando datos en Lista");
        String id = "";
        try {
            id = (DrawPaneTH.getLabelArasaacId((JLabel)support.getTransferable().getTransferData(DataFlavor.imageFlavor)));
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(DragImageList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DragImageList.class.getName()).log(Level.SEVERE, null, ex);
        }

        // En caso de que no estemos moviendo un icono
        // ni estemos importando uno de arasaac
        // entonces salir con false
        if (!canImport(support) || (inDrag != true) && (ArasaacTH.getDragging() != true)) {
            return false;
        }

        // En caso de que estemos importando de arasaac
        // y no estemos moviendo un icono de la propia lista
        // meterlo en nuestra lista favoritos
        if (ArasaacTH.getDragging() == true && inDrag == false) {
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int dropIndex = dl.getIndex();
            try {
                /*
                Guardar la imagen original del arasaac
                 */
                ImageIcon originalIcon = new ImageIcon(ArchivosArasaac.getInstance().getLocalImage(Integer.parseInt(id)));
                BufferedImage bI = new BufferedImage(originalIcon.getIconWidth(), originalIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics g = bI.createGraphics();
                originalIcon.paintIcon(null, g, 0, 0);
                g.dispose();
                /*
                Añade imagen a la lista
                */
                JLabel arasaacLabel = (JLabel) support.getTransferable().getTransferData(DataFlavor.imageFlavor);
                Path p = Paths.get(controller.getAm().getRootPath().concat(File.separator).concat(DrawPaneTH.getLabelArasaacId(arasaacLabel).concat(".png")));
                System.out.println("hemos recibido desde arasaac: " + arasaacLabel);
                
                model.add(dropIndex, arasaacLabel);
                /*
                Y guarda la imagen en ese nombre de archivo
                 */
                controller.getAm().anadeList(dropIndex, p, bI);

            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(DragImageList.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                Logger.getLogger(DragImageList.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        if (ArasaacTH.getDragging() != true && inDrag == true) {
            // Si no se da ninguno de los anteriores
            // significa que estamos moviendo dentro de la propia lista
            // entonces borrar en posición origen y añadir en posición destino
            try {
                JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                int dropIndex = dl.getIndex();
                if (dropIndex > dragIndex) {
                    dropIndex = dropIndex - 1;
                }
                model.removeElement(draggedIm);
                model.add(dropIndex, draggedIm);
                controller.getAm().mueveList(dragIndex, dropIndex);
                dragIndex = -1;
                return true;
            } catch (Exception e) {
                dragIndex = -1;
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {

        System.out.println("Lista terminando de exportar");
        super.exportDone(source, data, action);

        if (ArasaacTH.getDragging() == false && inDrag == true) {
            inDrag = false;
            DRAGGING_THIS = false;
            mDel.setVisible(false);

            System.out.println("flag de que se tiró a la papelera es: " + DragImageList.DROP_EN_BORRAR);
            if (DragImageList.DROP_EN_BORRAR == true) {
                System.out.println("borramos este icono de DragImageList...");
                JLabel l = (JLabel) draggedIm;
                int n = Integer.parseInt(l.getText());
                Path path = Paths.get(controller.getAm().idToUri(n).getPath());
                System.out.println(path);
                controller.getAm().borraList(path);
                model.removeElement(draggedIm);
            }
            DragImageList.DROP_EN_BORRAR = false;
            DragImageList.DRAGGING_THIS = false;
        }
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        System.out.println("Lista crea un transferable from: " + comp.toString());

        draggedIm = previewList.getSelectedValue();
        dragIndex = model.indexOf(draggedIm);
        inDrag = true;
        DRAGGING_THIS = true;
        mDel.setVisible(true);
        return this;
    }

    /*
    La parte de implementación del transferable
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        System.out.println("is data flavor supported by Lista?");
        return FLAVORS[0].equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor mFlavor) {
        System.out.println("get transfer data de Lista");
        return previewList.getModel().getElementAt(dragIndex);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        //System.out.println("obteniendo array de los flavors de Lista...");
        return FLAVORS;
    }

}
