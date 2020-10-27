/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import teoytea.views.ImagePane;

/**
 *
 * @author miguel
 */
public class ImagePaneModel {
    
    List<ImgCoordenatica> imagenes;
    ImagePane mPanel;
    
    public ImagePaneModel(ImagePane pane){
        imagenes = new ArrayList<>();
        mPanel = pane;
    }
    
    public void addImage(Image image, Point punto){
        imagenes.add(new ImgCoordenatica(image, punto));
        mPanel.setImageIndex(imagenes.size()-1);
        mPanel.repaint();
    }
    
    public int getImgQty(){
        return imagenes.size();
    }
    
    public Image getImg(int n){
        return imagenes.get(n).getImage();
    }
    
    public Point getPunto(int n){
        return imagenes.get(n).getPoint();
    }
    
}
