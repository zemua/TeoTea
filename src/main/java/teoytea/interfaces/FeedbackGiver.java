/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.interfaces;

import java.util.List;

/**
 *
 * @author miguel
 */
public interface FeedbackGiver<T> {
    // private List<FeedbackListener> listeners = new ArrayList<>();
    public void addFeedbackListener(FeedbackListener toAdd);
    public void giveFeedback(List<FeedbackListener> listeners, T feedback);
}
