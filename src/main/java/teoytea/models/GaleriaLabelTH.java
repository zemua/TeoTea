/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import static java.awt.datatransfer.DataFlavor.stringFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import teoytea.interfaces.BorranteGiver;
import teoytea.interfaces.BorranteListener;

/**
 *
 * @author miguel
 */
public class GaleriaLabelTH extends TransferHandler implements BorranteGiver<JLabel> {

    private static final DataFlavor[] FLAVORS = {imageFlavor, stringFlavor};
    List<BorranteListener> borrantelisteners = new ArrayList<>();

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == TransferHandler.MOVE) {
            JPanel parent = (JPanel) source.getParent();
            parent.remove(source);
            parent.revalidate();
            parent.repaint();
            giveBorrante(borrantelisteners, (JLabel) source);
        }
    }

    @Override
    public int getSourceActions(JComponent c) {
        //System.out.println("get source actions de Galeria Label");
        return TransferHandler.MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return false;
    }

    @Override
    public boolean importData(TransferSupport support) {
        return false;
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return FLAVORS;
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor arg0) {
                for (DataFlavor flavor : FLAVORS){
                    if (arg0.equals(flavor)) return true;
                }
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {
                if (isDataFlavorSupported(arg0))
                {
                    return comp;
                }
                return null;
            }
        };
    }

    @Override
    public void addBorranteListener(BorranteListener toAdd) {
        borrantelisteners.add(toAdd);
    }

    @Override
    public void giveBorrante(List<BorranteListener> listeners, JLabel feedback) {
        listeners.forEach((listener)->{
            listener.dandoBorrante(feedback);
        });
    }
}
