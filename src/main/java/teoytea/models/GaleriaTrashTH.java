/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import static java.awt.datatransfer.DataFlavor.stringFlavor;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author miguel
 */
public class GaleriaTrashTH extends TransferHandler {

    private static final DataFlavor[] FLAVORS = {imageFlavor, stringFlavor};

    @Override
    public int getSourceActions(JComponent c) {
        //System.out.println("get source actions de Galeria Label");
        return TransferHandler.NONE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        for (DataFlavor flavor : FLAVORS) {
            if (support.isDataFlavorSupported(flavor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean importData(TransferSupport support) {
        return true;
    }
}
