/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import teoytea.interfaces.ComposicionFeedbackGiver;
import teoytea.interfaces.ComposicionFeedbackListener;
import teoytea.interfaces.ZsOrdenSetter;
import teoytea.models.DrawPaneTH;
import teoytea.models.db.DBFlechas;
import teoytea.models.db.Zordenante;

/**
 *
 * @author miguel
 */
public class PintaControl implements ZsOrdenSetter, ComposicionFeedbackGiver {

    private static PintaControl este = null;
    private boolean modoPintar = false;
    private boolean pintando = false;
    private boolean in = false;
    private Point origen;
    private Point destino;
    private JPanel panel;
    private MouseMotionListener mml;
    private MouseListener ml;
    private JButton mBotonBorrar = null;
    private JToggleButton mTb = null;
    private JLabel jl;
    private int idComposicion;
    private DBFlechas dbflch;
    List<ComposicionFeedbackListener> listeners = new ArrayList<>();

    private PintaControl(JButton botonBorrar, JToggleButton tb, int idComposicion) {
        mBotonBorrar = botonBorrar;
        mTb = tb;
        this.idComposicion = idComposicion;
        dbflch = new DBFlechas();
    }

    public static PintaControl getInstance(JButton botonBorrar, JToggleButton tb, int idComposicion) {
        if (este != null) {
            este.mBotonBorrar = botonBorrar;
            este.mTb = tb;
        } else {
            este = new PintaControl(botonBorrar, tb, idComposicion);
        }
        este.idComposicion = idComposicion;
        return este;
    }

    public static PintaControl getInstance() {
        if (este == null || este.mBotonBorrar == null || este.mTb == null) {
            return null;
        }
        return este;
    }

    public boolean isMouseWithinComponent(Component c) {
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Rectangle bounds = c.getBounds();
        bounds.setLocation(c.getLocationOnScreen());
        return bounds.contains(mousePos);
    }

    public boolean isMouseWithinComponent() {
        return isMouseWithinComponent(panel);
    }

    public void ratonExited(Component c) {
        in = false;
        if (!isMouseWithinComponent(panel)) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void ratonEntered(Component c) {
        in = true;
        if (modoPintar && c.getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else if (!modoPintar && c.getCursor().getType() != Cursor.DEFAULT_CURSOR) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void ratonReleased(Component c) {
        if (pintando && modoPintar) { // En este caso añadir a la base de datos
            long idFlecha = dbflch.anhadirFlechaToComp(idComposicion, (int) origen.getX(), (int) origen.getY(), (int) destino.getX(), (int) destino.getY(), Math.min((int) origen.getX(), (int) destino.getX()), Math.min((int) origen.getY(), (int) destino.getY()), Pintamelo.getColor());
            DrawPaneTH.setLabelDbId(jl, (int) idFlecha);
            DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.FLECHA);
            System.out.println("nueva flecha en la base de datos con id: " + idFlecha);
            // Registra el orden de las Zs
            //JPanel mpanel = PictoController.getContenetor(c).obtenCanvas();
            setOrdenZsEnDb(panel);
            giveComposicionFeedback(listeners, panel, "", idComposicion);
        }
        pintando = false;
        modoPintar = false;
        mTb.setSelected(false);
        mTb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/flecha.png")));
        if (c.getCursor().getType() != Cursor.DEFAULT_CURSOR) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            jl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void ratonPressed() {
        if (modoPintar && isMouseWithinComponent(panel)) {
            pintando = true;
            origen = panel.getMousePosition();
            jl = DrawPaneTH.adaptarLabelTh(new JLabel(), mBotonBorrar, idComposicion);
        }
    }

    public void ratonClicked(Component c) {
        if (modoPintar) {
            mTb.setSelected(false);
            mTb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/flecha.png")));
            modoPintar = false;
        }
        if (c.getCursor().getType() != Cursor.DEFAULT_CURSOR) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void ratonMoved(Component c) {
        in = true;
        if (modoPintar && c.getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else if (!modoPintar && c.getCursor().getType() != Cursor.DEFAULT_CURSOR) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void ratonDragged(Component c) {
        if (modoPintar && pintando && isMouseWithinComponent()) {
            destino = panel.getMousePosition();
            Pintamelo.meLoPintas(new Point(origen.x, origen.y), new Point(destino.x, destino.y), Pintamelo.FLECHA, jl);
            Pintamelo.labelToPanel(jl, panel);
        }
    }

    public void setJpanel(JPanel mpan) {
        panel = mpan;
        /*
        Acciones de click y entrada salida
         */
        if (ml == null) {
            ml = new MouseListener() {
                @Override
                public void mouseExited(MouseEvent e) {
                    ratonExited(panel);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    ratonEntered(panel);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    ratonReleased(panel);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    ratonPressed();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    ratonClicked(panel);
                }
            };
        }
        /*
        Acciones de movimiento y arrastrar
         */
        if (mml == null) {
            mml = new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    //System.out.println("mouse moved");
                    ratonMoved(panel);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    //System.out.println("mouse dragged");
                    //dibujar flecha
                    ratonDragged(panel);
                }
            };
        }
    }

    /*
        Meterle las acciones al panel
     */
    private void ponListeners() {
        panel.addMouseListener(ml);
        panel.addMouseMotionListener(mml);
    }

    private void quitaListeners() {
        panel.removeMouseListener(ml);
        panel.removeMouseMotionListener(mml);
    }

    public void entraModoPintar() {
        modoPintar = true;
        ponListeners();
    }

    public void dejaModoPintar() {
        modoPintar = false;
        pintando = false;
        quitaListeners();
    }

    public boolean getModoPintar() {
        return modoPintar;
    }

    @Override
    public void setOrdenZsEnDb(JPanel panel) {
        Zordenante.setOrdenZs(panel);
    }

    @Override
    public void addComposicionFeedbackListener(ComposicionFeedbackListener toAdd) {
        listeners.add(toAdd);
    }

    @Override
    public void giveComposicionFeedback(List<ComposicionFeedbackListener> listeners, JPanel panel, String titulo, int id) {
        listeners.forEach((listener)->{
            listener.dandoFeedback(panel, titulo, id);
        });
    }
}
