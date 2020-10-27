/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.interfaces;

/**
 *
 * @author miguel
 * @param <T>
 */
public interface MyFeedbacker<T> {
    public void addMyFeedbackListener(MyFeedbackListener<T> toAdd);
    public void giveMyFeedback(int tipo, T feedback);
}
