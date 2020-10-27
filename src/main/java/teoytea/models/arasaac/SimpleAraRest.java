/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;
import teoytea.models.ArchivosModel;

/**
 *
 * @author miguel
 */
public class SimpleAraRest implements MyFeedbacker<JLabel> {

    Client client;
    List<Integer> pictoList = new ArrayList<>();
    Invocation.Builder builder = null;
    static ArasaacInvocationCallback icb = null;
    public static final String PLACEHOLDER = "cargando-placeholder";
    public static final String ERRORPH = "error-placeholder";
    private boolean pictoListCerrada = false;
    private boolean limpiado = false;
    private static int CONSULTA_ID = 0;
    private int idLocal;
    ArrayList<MyFeedbackListener<JLabel>> feedbackListeners = new ArrayList<>();

    public static final int TIPO_ADD_LABEL = 0;
    public static final int TIPO_LIMPIA_PLACEHOLDER = 1;
    public static final int TIPO_ADD_ERROR = 2;
    public static final int TIPO_TIME_OUT = 3;
    public static final int TIPO_CLEAR_LIST = 4;
    public static final int TIPO_REPAINT_LIST = 5;

    public SimpleAraRest() {
        CONSULTA_ID += 1;
        idLocal = CONSULTA_ID;
        /**
         * Si los ids son diferentes es un momento dado, significa que hay al
         * menos una consulta más nueva que esta.
         */

        if (icb != null && !icb.checkAllThreadsFinished()) {
            icb.interrumpir();
        }
        icb = new ArasaacInvocationCallback();
    }

    /**
     * Llamar para iniciar
     */
    public void vamos(String mTarget) {

        // para empezar de nuevo
        giveMyFeedback(TIPO_CLEAR_LIST, null);

        // poer label cargando
        JLabel mjl = new JLabel();
        ImageIcon imic = new ImageIcon(SimpleAraRest.this.getClass().getResource("/teoytea/resources/aux/loading.gif"));
        imic = ArchivosModel.scaleImage(imic, ArchivosModel.P_WIDTH);
        mjl.setIcon(imic);
        mjl.setText(PLACEHOLDER);
        giveMyFeedback(TIPO_ADD_LABEL, mjl);
        imic.setImageObserver((img, flags, x, y, w, h) -> {
            giveMyFeedback(TIPO_REPAINT_LIST, null);
            return true;
        });
        client = ClientBuilder.newClient();
        WebTarget myResource = getWebtarget("https://api.arasaac.org/api/pictograms/es/search/" + mTarget);
        builder = myResource.request(MediaType.APPLICATION_JSON_TYPE);
        icb.addMyFeedbackListener((MyFeedbackListener<JLabel>) (int tipo, JLabel feedback) -> {
            switch (tipo) {
                case ArasaacInvocationCallback.TIPO_ADD:
                    addElement(feedback);
                    break;
                case ArasaacInvocationCallback.TIPO_FAIL:
                    anhadeErrorLabel();
                    break;
                case ArasaacInvocationCallback.TIPO_LIMPIA:
                    //limpiaPlaceholder();
                    break;
                case ArasaacInvocationCallback.TIPO_CLOSE_CLIENT:
                    client.close();
                    break;
            }
        });
        builder.async().get(icb);
        setTimeOut(5000); // 5 segundos para recuperar las imágenes
    }

    public boolean permisoParaLimpiar() {
        boolean permiso = !limpiado;
        limpiado = true;
        return permiso;
    }

    public void clearPermisoParaLimpiar() {
        limpiado = false;
    }

    public boolean checkAllThreadsFinished() {
        boolean ret = (icb != null && icb.checkAllThreadsFinished() && esLaMasReciente());
        return ret;
    }

    public int getId() {
        return CONSULTA_ID;
    }

    public int getLocalId() {
        return idLocal;
    }

    public boolean esLaMasReciente() {
        return (idLocal == CONSULTA_ID);
    }

    private WebTarget getWebtarget(String mTarget) {
        return client.target(mTarget);
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

    List<Integer> getPictoList() {
        return pictoList;
    }

    void cierraPicto() {
        pictoListCerrada = true;
    }

    void anhadeErrorLabel() {
        try {
            giveMyFeedback(TIPO_ADD_ERROR, getErrorLabel());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excepción añadiendo error label");
        }
    }

    public ImageIcon getErrorImage() {
        return new ImageIcon(SimpleAraRest.this.getClass().getResource("/teoytea/resources/aux/error.jpeg"));
    }

    JLabel getErrorLabel() {
        JLabel errorJl = new JLabel();
        errorJl.setText(SimpleAraRest.ERRORPH);
        ImageIcon imic = getErrorImage();
        imic = ArchivosModel.scaleImage(imic, ArchivosModel.P_WIDTH);
        errorJl.setIcon(imic);
        return errorJl;
    }

    void setTimeOut(int tiempo) {
        RemovePlaceHolderWaiter borrante = new RemovePlaceHolderWaiter(tiempo);
        borrante.addMyFeedbackListener((MyFeedbackListener<Object>) (int tipo, Object feedback) -> {
            switch (tipo) {
                case RemovePlaceHolderWaiter.TIPO_CERRAR:
                    if (esLaMasReciente()) {
                        giveMyFeedback(TIPO_TIME_OUT, getErrorLabel());
                        icb.interrumpir();
                    }
                    break;
            }
        });
        borrante.execute();
    }

    public void limpiaPlaceholder() {
        giveMyFeedback(TIPO_LIMPIA_PLACEHOLDER, getErrorLabel());
    }

    void addElement(JLabel addLabel) {
        try {
            giveMyFeedback(TIPO_ADD_LABEL, addLabel);
            if (checkAllThreadsFinished() && esLaMasReciente()) {
                giveMyFeedback(TIPO_LIMPIA_PLACEHOLDER, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excepción añadiendo JLabel");
        }
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener toAdd) {
        feedbackListeners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, JLabel feedback) {
        feedbackListeners.forEach((listener) -> {
            listener.giveMyFeedback(tipo, feedback);
        });
    }

}
