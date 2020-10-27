/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.ws.rs.client.InvocationCallback;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;

/**
 *
 * @author miguel
 *
 */
class ArasaacInvocationCallback implements InvocationCallback<String>, MyFeedbacker<JLabel> {

    static SimpleImageFetcher sif = null;
    List<Integer> pictoList = new ArrayList<>();
    private boolean pictoListCerrada = false;

    private ArrayList<MyFeedbackListener<JLabel>> feedbacklisteners = new ArrayList<>();

    public static final int TIPO_FAIL = 0;
    public static final int TIPO_LIMPIA = 1;
    public static final int TIPO_ADD = 2;
    public static final int TIPO_CLOSE_CLIENT = 3;

    ArasaacInvocationCallback() {
    }

    @Override
    public void completed(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        try {
            ArasaacObject[] picto = objectMapper.readValue((java.lang.String) s, ArasaacObject[].class);
            for (ArasaacObject ao : picto) {
                addPicto(ao.getId());
            }
            cierraPicto();
            goRender();
            giveMyFeedback(TIPO_CLOSE_CLIENT, null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: ArasaacInovactionCallback IOException");
            e.printStackTrace();
        }
    }

    void cierraPicto() {
        pictoListCerrada = true;
    }

    List<Integer> getPictoList() {
        return pictoList;
    }

    void addPicto(Integer i) {
        try {
            if (!pictoListCerrada) {
                pictoList.add(i);
            } else {
                System.out.println("tratando de añadir pictos con lista cerrada");
            }
        } catch (Exception e) {
            System.out.println("excepción añadiendo picto");
            System.out.println(e);
        }
    }

    public boolean checkAllThreadsFinished() {
        return (sif != null && sif.checkAllThreadGaveFeddback());
    }

    SimpleImageFetcher getSif() {
        return sif;
    }

    @Override
    public void failed(Throwable throwable) {
        System.out.println("error intentando acceder a arasaac");
        anhadeErrorLabel();
    }

    void anhadeErrorLabel() {
        giveMyFeedback(TIPO_FAIL, null);
    }

    void limpiaPlaceholder() {
        giveMyFeedback(TIPO_LIMPIA, null);
    }

    void addElement(JLabel addLabel) {
        giveMyFeedback(TIPO_ADD, addLabel);

    }

    private void goRender() {
        List<Integer> mList = getPictoList();
        Integer[] mArray = mList.toArray(new Integer[mList.size()]);
        sif = new SimpleImageFetcher(mArray);
        sif.addMyFeedbackListener((MyFeedbackListener<JLabel>) (i, label) -> {
            switch (i) {
                case SimpleImageFetcher.TIPO_ANHADE:
                    addElement(label);
                    break;
                case SimpleImageFetcher.TIPO_LIMPIA:
                    limpiaPlaceholder();
                    break;
                case SimpleImageFetcher.TIPO_ERROR:
                    anhadeErrorLabel();
                    break;
            }
        });
    }

    void interrumpir() {
        if (sif != null) {
            sif.interrumpir();
        }
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
