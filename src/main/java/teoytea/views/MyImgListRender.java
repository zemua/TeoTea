/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.views;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author miguel
 */
public class MyImgListRender extends DefaultListCellRenderer {

    private String mFile;
    
    public MyImgListRender() {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(((JLabel)value).getIcon());
        label.setName(((JLabel)value).getName());
        //label.setText(((JLabel)value).getText());
        label.setText("");
        return label;
    }
}
