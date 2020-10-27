/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.interfaces;

/**
 *
 * @author miguel
 */
public interface ComposicionFeedbackObserver {
    public void anhadeComposicionFeedbackObserver(ComposicionFeedbackGiver fg);
        //fg.addComposicionFeedbackListener((composicionFeedbackListener<Integer>) (Integer feedback) -> { // en clase
            // Aquí lo que quieras hacer con feedback // en clase
            //});
        //}
}
