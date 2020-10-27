/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;

/**
 *
 * @author miguel
 */
public class BorrarTH extends TransferHandler implements MyFeedbacker<BorrarTH> {
    
    private static final DataFlavor[] FLAVORS = {imageFlavor};
    
    ArrayList<MyFeedbackListener<BorrarTH>> feedbacklisteners = new ArrayList<>();
    
    public static final int TIPO_BORRADO = 0;
    
    public BorrarTH(){
        
    }
    
    @Override
    public int getSourceActions(JComponent c) {
        System.out.println("get source actions de BorrarTH");
        return TransferHandler.MOVE;
    }
    
    @Override
    public boolean canImport(TransferSupport support) {
        System.out.println("BorrarTH can import?");
        
        if (!support.isDataFlavorSupported(FLAVORS[0])) {
            System.out.println("no, cannot import");
            return false;
        }

        System.out.println("yes I can!");
        return true;
    }
    
    @Override
    public boolean importData(TransferSupport support) {
        System.out.println("BorrarTH importing data...");
        if (DragImageList.DRAGGING_THIS == true){
            System.out.println("poniendo flag de que se tiró imagen de lista a papelera...");
            DragImageList.DROP_EN_BORRAR = true;
        } else{
            giveMyFeedback(TIPO_BORRADO, this);
        }
        return true;
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener<BorrarTH> toAdd) {
        feedbacklisteners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, BorrarTH feedback) {
        feedbacklisteners.forEach((listener)->{
            listener.giveMyFeedback(tipo, feedback);
        });
    }
}
