/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JList;

/**
 *
 * @author miguel
 */
public class ArasaacTransferable implements Transferable {
    
    private static final DataFlavor[] FLAVORS = {imageFlavor};
    JList mList;
    int dragIndex;
    
    public ArasaacTransferable(JList list, int index){
        super();
        mList = list;
        dragIndex = index;
    }
    
    @Override
    public Object getTransferData(DataFlavor flavor){
        System.out.println("consiguiendo transferable de arasaac...");
        System.out.println(mList.getModel().getElementAt(dragIndex));
        return mList.getModel().getElementAt(dragIndex);
    }
    
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        System.out.println("is data flavor supported by arasaac?");
        return FLAVORS[0].equals(flavor);
    }
    
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        System.out.println("obteniendo array de los flavors de arasaac...");
        return FLAVORS;
    }
}
