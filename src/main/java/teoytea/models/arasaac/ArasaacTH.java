/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.awt.datatransfer.Transferable;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 *
 * @author miguel
 */
public class ArasaacTH extends TransferHandler {

    JList mList;
    DefaultListModel mModel;
    public static boolean dragging = false;
    public static int dragIndex = -1;

    public ArasaacTH(JList list, DefaultListModel model) {
        super();
        mList = list;
        mModel = model;
    }
    
    public static boolean getDragging(){
        return dragging;
    }

    @Override
    public int getSourceActions(JComponent c) {
        System.out.println("get source actions de arasaac");
        return TransferHandler.COPY;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        System.out.println("can arasaac import? siempre false");
        return false;
    }
    
    @Override
    public Transferable createTransferable(JComponent comp) {
        System.out.println("arasaac crea un transferable from: " + comp.toString());
        if(mList.getSelectedValue().getClass() == JLabel.class && (((JLabel)mList.getSelectedValue()).getText() == SimpleAraRest.ERRORPH || ((JLabel)mList.getSelectedValue()).getText() == SimpleAraRest.PLACEHOLDER)){
            return null;
        }
        dragging = true;
        dragIndex = mModel.indexOf(mList.getSelectedValue());
        return new ArasaacTransferable(mList, mModel.indexOf(mList.getSelectedValue()));
    }
    
    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        System.out.println("Lista terminando de exportar");
        super.exportDone(source, data, action);
        dragging = false;
        dragIndex = -1;
    }
}
