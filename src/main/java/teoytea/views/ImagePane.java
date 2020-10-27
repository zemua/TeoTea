/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import teoytea.models.ImagePaneModel;

/**
 *
 * @author miguel
 */
public class ImagePane extends JPanel {

    ImagePaneModel model = new ImagePaneModel(this);
    int nImage;

    public ImagePane() {

    }

    public ImagePaneModel getModel() {
        return model;
    }
    
    public void setImageIndex(int n){
        nImage = n;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image estaImagen = model.getImg(nImage);
        Point estePunto = model.getPunto(nImage);
        if (estaImagen != null && estePunto != null) {
            Point p = SwingUtilities.convertPoint(getParent(), estePunto, this);
            g.drawImage(estaImagen, p.x, p.y, this);
        }
    }

}
