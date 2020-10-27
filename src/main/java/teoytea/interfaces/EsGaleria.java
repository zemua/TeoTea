/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.interfaces;

import javax.swing.JPanel;

/**
 *
 * @author miguel
 */
public interface EsGaleria {
    public <T extends JPanel> T getCanvas();
    public void setController(ControladorInterface c);
}
