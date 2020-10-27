/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.views;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author miguel
 */
public class ColorCellRenderer extends DefaultListCellRenderer {
    
    public static final String ROJO = "Rojo";
    public static final String VERDE = "Verde";
    public static final String AMARILLO = "Amarillo";
    public static final String NARANJA = "Naranja";
    public static final String AZUL = "Azul";
    public static final String NEGRO = "Negro";
    
    public ColorCellRenderer(){
        super();
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        switch (label.getText()){
            case ColorCellRenderer.ROJO:
                label.setBackground(Color.RED);
                break;
            case ColorCellRenderer.VERDE:
                label.setBackground(Color.GREEN);
                break;
            case ColorCellRenderer.AMARILLO:
                label.setBackground(Color.YELLOW);
                break;
            case ColorCellRenderer.NARANJA:
                label.setBackground(Color.ORANGE);
                break;
            case ColorCellRenderer.AZUL:
                label.setBackground(Color.CYAN);
                break;
            case ColorCellRenderer.NEGRO:
                label.setBackground(Color.BLACK);
                break;
            default:
                label.setBackground(Color.BLACK);
                break;
        }
        return label;
    }
}
