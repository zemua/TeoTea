/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import teoytea.interfaces.BorranteGiver;
import teoytea.interfaces.BorranteListener;
import teoytea.interfaces.BorranteObserver;
import teoytea.interfaces.FeedbackGiver;
import teoytea.interfaces.FeedbackListener;
import teoytea.interfaces.FeedbackObserver;
import teoytea.models.ArchivosModel;
import teoytea.models.GaleriaLabelTH;
import teoytea.models.GaleriaTrashTH;
import teoytea.models.db.DBcomposicion;
import teoytea.models.db.DBgaleria;
import teoytea.models.dibujos.ArchivosComposicion;
import teoytea.models.dibujos.ColorEnum;
import teoytea.models.dibujos.Pintamelo;
import teoytea.views.GaleriaView;

/**
 *
 * @author miguel
 */
public class GaleriaController implements FeedbackGiver<Integer>, FeedbackObserver, BorranteObserver {

    GaleriaView galeria;
    List<FeedbackListener> mlistener = new ArrayList<>();

    GaleriaController(GaleriaView galeria) {
        this.galeria = galeria;
        Pintamelo.setColor(ColorEnum.Cenum.NEGRO);
        anhadeFeedbackObserver(this.galeria);
    }

    void load() {
        try {
            JPanel comp = galeria.getCanvas();
            ResultSet rs = DBgaleria.getInstance().getAllDibujos();
            while (rs.next()) {
                JLabel jl = new JLabel();
                setLabelId(jl, rs.getInt("ID"));
                setLabelDisplayText(jl, rs.getString("NOMBRE"));
                jl.setHorizontalAlignment(JLabel.CENTER);
                jl.setHorizontalTextPosition(JLabel.CENTER);
                jl.setVerticalTextPosition(JLabel.BOTTOM);
                ponerIcono(jl, getLabelId(jl));
                GaleriaLabelTH handler = new GaleriaLabelTH();
                jl.setTransferHandler(handler);
                addListenerTrasher(jl);
                galeria.getTrash().setTransferHandler(new GaleriaTrashTH());
                anhadeBorranteObserver(handler);
                comp.add(jl);
                addListenerDibujo(jl);
                System.out.println("añadido dibujo: " + rs.getString("NOMBRE"));
            }
            comp.revalidate();
            comp.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(GaleriaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setLabelDisplayText(JLabel jl, String txt) {
        jl.setText(txt);
    }

    void setLabelId(JLabel jl, int id) {
        jl.setName(Integer.toString(id));
    }

    int getLabelId(JLabel jl) {
        return Integer.parseInt(jl.getName());
    }

    private void addListenerDibujo(JLabel jl) {
        jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = getLabelId(jl);
                System.out.println("clickado dibujo " + id);
                //eventoBoton(PictoController.Accion.Open, id);
                giveFeedback(mlistener, id);
            }
        });
    }

    private void addListenerTrasher(JLabel jl) {
        jl.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                jl.getTransferHandler().exportAsDrag(jl, e, TransferHandler.MOVE);
            }
        });
    }

    @Override
    public void addFeedbackListener(FeedbackListener toAdd) {
        mlistener.add(toAdd);
    }

    @Override
    public void giveFeedback(List<FeedbackListener> listeners, Integer accion) {
        listeners.forEach((listener) -> {
            listener.dandoFeedback(accion);
        });
    }

    @Override
    public final void anhadeFeedbackObserver(FeedbackGiver fg) {
        fg.addFeedbackListener((FeedbackListener<Integer>) (Integer feedback) -> {
            giveFeedback(mlistener, feedback);
        });
    }

    JLabel ponerIcono(JLabel label, int id) {
        ArchivosComposicion arch = new ArchivosComposicion();
        ImageIcon icon = arch.getComposicionIcon(id);
        icon = ArchivosModel.scaleImage(icon, 100);
        label.setIcon(icon);
        return label;
    }

    @Override
    public void anhadeBorranteObserver(BorranteGiver fg) {
        fg.addBorranteListener((BorranteListener<JLabel>) (JLabel feedback)->{
            new ArchivosComposicion().borraImagen(getLabelId(feedback));
            new DBcomposicion().borrarComp(getLabelId(feedback));
        });
    }
}
