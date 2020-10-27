/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;

/**
 *
 * @author miguel
 */
public class SimpleImageFetcher implements MyFeedbacker<JLabel> {

    static FetchImageWorker[] fetchers = null;

    public static final int TIPO_ERROR = 0;
    public static final int TIPO_ANHADE = 1;
    public static final int TIPO_LIMPIA = 2;

    private ArrayList<MyFeedbackListener<JLabel>> feedbacklisteners = new ArrayList<>();

    public static final URL arasaacUrlFromId(int id) {
        try {
            return new URL("https://api.arasaac.org/api/pictograms/" + id + "?download=false");
        } catch (MalformedURLException ex) {
            Logger.getLogger(SimpleImageFetcher.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    SimpleImageFetcher(Integer[] ids) {
        fetchers = new FetchImageWorker[ids.length];
        for (int i = 0; i < ids.length; i++) {
            URL mUrl;
            mUrl = arasaacUrlFromId(ids[i]);
            FetchImageWorker mRun = new FetchImageWorker(mUrl, ids[i]);
            fetchers[i] = mRun;
            mRun.addMyFeedbackListener((MyFeedbackListener<JLabel>) (tipo, label)->{
                switch (tipo){
                    case FetchImageWorker.TIPO_ADD:
                        addElement(label);
                        break;
                }
            });
            mRun.execute();
        }
    }

    public boolean checkAllThreadsFinished() {
        for (FetchImageWorker fetcher : fetchers) {
            if (!fetcher.isDone() && !fetcher.isCancelled()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAllThreadGaveFeddback() {
        return checkAllThreadsFinished();
    }

    void addElement(JLabel label) {
        giveMyFeedback(TIPO_ANHADE, label);
    }

    void limpiaPlaceholder() {
        giveMyFeedback(TIPO_LIMPIA, null);
    }

    void interrumpir() {
        if (fetchers != null) {
            for (FetchImageWorker fetcher : fetchers) {
                if (fetcher != null) {
                    if (!fetcher.isDone() && !fetcher.isCancelled()) {
                        fetcher.cancel(true);
                        System.out.println("fetcher cancelado por interrupción");
                    }
                }
            }
        }
    }

    FetchImageWorker[] getFetchers() {
        return fetchers;
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener<JLabel> toAdd) {
        feedbacklisteners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, JLabel feedback) {
        feedbacklisteners.forEach((listener) -> {
            listener.giveMyFeedback(tipo, feedback);
        });
    }
}
