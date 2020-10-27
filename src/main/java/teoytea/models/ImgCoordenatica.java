/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author miguel
 */
public class ImgCoordenatica {
    
    Image mImg;
    Point mPt;
    
    public ImgCoordenatica (Image img, Point pt){
        mImg = img;
        mPt = pt;
    }
    
    public void setImage(Image img){
        mImg = img;
    }
    
    public void setPoint(Point pt){
        mPt = pt;
    }
    
    public Image getImage(){
        return mImg;
    }
    
    public Point getPoint(){
        return mPt;
    }
}
