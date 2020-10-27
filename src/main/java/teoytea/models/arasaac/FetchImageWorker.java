/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;
import teoytea.models.ArchivosModel;
import teoytea.models.DrawPaneTH;

/**
 *
 * @author miguel
 */
public class FetchImageWorker extends SwingWorker<Void, Void> implements MyFeedbacker<JLabel> {

    JLabel mLabel;
    URL mUrl;
    int id;
    
    public static final int TIPO_ADD = 0;
    
    ArrayList<MyFeedbackListener<JLabel>> feedbackListeners = new ArrayList<>();
    
    public FetchImageWorker(URL url, int mId){
        mUrl = url;
        id = mId;
    }
    
    @Override
    protected Void doInBackground() {
        
        try {
            BufferedImage image;
            if (!ArchivosArasaac.getInstance().<Integer>localExists(id)) {
                image = ImageIO.read(mUrl);
                ArchivosArasaac.getInstance().saveImage(image, Integer.toString(id));
            } else {
                image = ArchivosArasaac.getInstance().<Integer>getLocalImage(id);
            }
            ImageIcon icon = ArchivosModel.scaleImage(new ImageIcon(image), ArchivosModel.P_WIDTH);
            mLabel = new JLabel();
            mLabel.setIcon(icon);
            DrawPaneTH.setLabelArasaacId(mLabel, Integer.toString(id));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(FetchImageWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @Override
    protected void done(){
        giveMyFeedback(TIPO_ADD, mLabel);
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener<JLabel> toAdd) {
        feedbackListeners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, JLabel feedback) {
        feedbackListeners.forEach((listener)->{
            listener.giveMyFeedback(tipo, feedback);
        });
    }
    
}
