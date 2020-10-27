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
public interface FeedbackObserver {
    public void anhadeFeedbackObserver(FeedbackGiver fg);
        //fg.addFeedbackListener((FeedbackListener<Integer>) (Integer feedback) -> {
            // Aquí lo que quieras hacer con feedback
            //});
        //}
}
