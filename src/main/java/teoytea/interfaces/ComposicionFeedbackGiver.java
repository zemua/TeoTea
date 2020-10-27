/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.interfaces;

import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author miguel
 */
public interface ComposicionFeedbackGiver {
    // private List<composicionFeedbackListener> listeners = new ArrayList<>(); // declarar en clase
    public void addComposicionFeedbackListener(ComposicionFeedbackListener toAdd);
    public void giveComposicionFeedback(List<ComposicionFeedbackListener> listeners, JPanel panel, String titulo, int id);
}
