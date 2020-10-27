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
public interface BorranteGiver<T> {
    // private List<FeedbackListener> listeners = new ArrayList<>(); // declarar en clase
    public void addBorranteListener(BorranteListener toAdd);
    public void giveBorrante(List<BorranteListener> listeners, T feedback);
}
