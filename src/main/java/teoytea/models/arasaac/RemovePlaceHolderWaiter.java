/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;

/**
 *
 * @author miguel
 */
public class RemovePlaceHolderWaiter extends SwingWorker<Object, Void> implements MyFeedbacker<Object> {

    DefaultListModel m;
    int id;
    SimpleAraRest sar;
    ArrayList<MyFeedbackListener<Object>> mylisteners = new ArrayList<>();
    int tiempo;
    
    public static final int TIPO_CERRAR = 0;

    RemovePlaceHolderWaiter(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener toAdd) {
        mylisteners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, Object feedback) {
        mylisteners.forEach((listener)->{
            listener.giveMyFeedback(tipo, feedback);
        });
    }

    @Override
    protected Object doInBackground() {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(RemovePlaceHolderWaiter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    protected void done(){
        giveMyFeedback(TIPO_CERRAR, this);
    }
}
