/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import teoytea.controllers.PictoController;
import teoytea.interfaces.ComposicionFeedbackGiver;
import teoytea.interfaces.ComposicionFeedbackListener;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.ZsOrdenSetter;
import teoytea.models.arasaac.ArchivosArasaac;
import teoytea.models.db.DBFlechas;
import teoytea.models.db.DBPictogramas;
import teoytea.models.db.DBbocadillos;
import teoytea.models.db.Zordenante;
import teoytea.models.dibujos.ColorEnum;
import teoytea.models.dibujos.PintaControl;
import teoytea.models.dibujos.Pintamelo;

/**
 *
 * @author miguel
 */
public class DrawPaneTH extends TransferHandler implements ZsOrdenSetter, ComposicionFeedbackGiver {

    static public final String FLECHA = "F";
    static public final String BOCADILLO = "B";
    static public final String NUBE = "N";

    private static final DataFlavor[] FLAVORS = {imageFlavor};
    JPanel myPane = new JPanel();
    List<String> labels = new ArrayList<>();
    JButton mDel;
    public static JButton sDel;
    public static JPanel sPane;
    private static JLabel labelArrastrada = null;
    public static boolean pulsado = false;
    private static Point origen;
    int mIdComposicion;
    private static DBPictogramas dbpcts;
    private static DBFlechas dbflchs;
    private static DBbocadillos dbbcts;
    List<ComposicionFeedbackListener> complisteners;
    static DrawPaneTH current;

    public DrawPaneTH(JPanel p, JButton del, int idComposicion) {
        sPane = myPane = p;
        sDel = mDel = del;
        mIdComposicion = idComposicion;
        dbpcts = new DBPictogramas(p, idComposicion);
        dbflchs = new DBFlechas();
        dbbcts = new DBbocadillos();
        complisteners = new ArrayList<>();
        current = this;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(FLAVORS[0]);
    }

    public static boolean attachLabelEnPanel(JPanel myPane, JButton mDel, int idComposicion, JLabel mlbl) {
        JLabel label1 = new JLabel();
        JLabel mIcono = mlbl;

        label1 = adaptarLabelTh(label1, mDel, idComposicion);
        try {
            label1.setIcon(mIcono.getIcon());
            setLabelArasaacId(label1, mIcono.getText());
        } catch (NullPointerException ex) {
            System.out.println(ex.toString());
        }
        Point dropLocation = myPane.getMousePosition();
        int halfWidth = (int) Math.round(label1.getIcon().getIconWidth() / 2);
        int halfHeight = (int) Math.round(label1.getIcon().getIconHeight() / 2);
        label1.setBounds(Math.round((float) (dropLocation.getX() - halfWidth)), (int) Math.round(dropLocation.getY() - halfHeight), label1.getIcon().getIconWidth(), label1.getIcon().getIconHeight());
        Pintamelo.labelToPanel(label1, myPane);

        /**
         * añadir a la base de datos las flechas se añaden en
         * PintaControl.ratonReleased(Component c)
         */
        int dbcomp = idComposicion;
        String drpTxt = PictoController.getTextoDropable(myPane).getDraggableText();
        switch (checkTipoLabel(label1)) {
            case Arasaac:
                long dbid = dbpcts.anhadirPictoToComp(dbcomp, Integer.parseInt(getLabelArasaacId(mIcono)), label1.getX(), label1.getY(), label1.getIcon().getIconWidth(), label1.getIcon().getIconHeight());
                setLabelDbId(label1, (int) dbid);
                System.out.println("añadida a db la label " + dbid);
                break;
            case Bocadillo:
                long bocadbid = dbbcts.anhadirBocadilloToComp(dbcomp, BOCADILLO, drpTxt, label1.getX(), label1.getY(), Pintamelo.getColor());
                setLabelDbId(label1, (int) bocadbid);
                System.out.println("añadida a db el bocadillo " + bocadbid);
                break;
            case Nube:
                long nubedbid = dbbcts.anhadirBocadilloToComp(dbcomp, NUBE, drpTxt, label1.getX(), label1.getY(), Pintamelo.getColor());
                setLabelDbId(label1, (int) nubedbid);
                System.out.println("añadida a db la nube " + nubedbid);
                break;
            case Flecha:
                long flechadbid = dbflchs.duplicate(getLabelDbId(mlbl), label1.getX(), label1.getY());
                setLabelDbId(label1, (int) flechadbid);
                System.out.println("añadida a db la flecha " + flechadbid);
                break;
        }
        // Registrar orden de las Zs
        Zordenante.setOrdenZs(myPane); // TODO error con flechas pasa aquí
        current.giveComposicionFeedback(current.complisteners, myPane, "", idComposicion);

        return true;
    }

    public static boolean attachLabelEnPanel(JPanel myPane, JButton mDel, TransferSupport support, int idComposicion) {
        JLabel mIcono = null;
        try {
            System.out.println("los datos del transferable... " + support.getTransferable().getTransferData(DataFlavor.imageFlavor));
            if (support.getTransferable().getTransferData(DataFlavor.imageFlavor) instanceof JLabel) {
                System.out.println("los datos a importar son JLabel");
                mIcono = (JLabel) support.getTransferable().getTransferData(DataFlavor.imageFlavor);
            } else { // si no es un jlabel cancela
                return false;
            }
        } catch (UnsupportedFlavorException ex) {
            System.out.println("Error, no era icon...");
            Logger.getLogger(DrawPaneTH.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Error de lectura/escritura...");
            Logger.getLogger(DrawPaneTH.class.getName()).log(Level.SEVERE, null, ex);
        }

        Zordenante.setOrdenZs(myPane);
        current.giveComposicionFeedback(current.complisteners, myPane, "", idComposicion);

        return attachLabelEnPanel(myPane, mDel, idComposicion, mIcono);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        return attachLabelEnPanel(myPane, mDel, support, mIdComposicion);
    }

    public static JLabel adaptarLabelTh(JLabel mlabel, JButton mborrar, int idCompos) {

        MouseListener listener = new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (PintaControl.getInstance() != null && !PintaControl.getInstance().getModoPintar()) {
                    pulsado = true; //arrastrar label
                    labelArrastrada = (JLabel) e.getSource();
                    origen = e.getPoint();
                }
                if (PintaControl.getInstance() != null && PintaControl.getInstance().getModoPintar()) {
                    PintaControl.getInstance().ratonPressed(); //pintar flecha
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { // al ir arrastrando el label, si salimos del panel, exportar drag
                if (pulsado == true && !PintaControl.getInstance().isMouseWithinComponent()) {
                    JComponent c = (JComponent) e.getSource(); // ImageLabel
                    TransferHandler handler = labelArrastrada.getTransferHandler(); // ImageLabelTH
                    handler.exportAsDrag(labelArrastrada, e, TransferHandler.MOVE);
                    /**
                     * Si no se usa la referencia "labelArrastrada" en algunas
                     * ocasiones, al arrastrar fuera por encima de otra label el
                     * sistema hace la exportación de la label por la que se
                     * pasó por encima, no de la que estamos arrastrando, con
                     * labelArrastrada nos aseguramos que exportamos la label
                     * correcta.
                     */
                    System.out.println("exported desde mouse exited");
                    pulsado = false;
                    labelArrastrada = null;
                    PintaControl.getInstance().ratonExited((JComponent) e.getSource());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                PintaControl.getInstance().ratonEntered(mlabel);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pulsado = false;
                labelArrastrada = null;
                sDel.setVisible(false);
                PintaControl.getInstance().ratonReleased(mlabel);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                labelArrastrada = null;
                PintaControl.getInstance().ratonClicked(mlabel);
            }
        };
        mlabel.addMouseListener(listener);

        MouseMotionListener mmL = new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                PintaControl.getInstance().ratonMoved(mlabel);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (pulsado) { // arrastrar label
                    JLabel c = (JLabel) e.getSource();
                    //System.out.println("origen: " + origen);
                    //System.out.println("x: " + e.getX() + " y: " + e.getY());
                    int objX = (int) (c.getLocation().getX() + e.getPoint().getX() - origen.getX());
                    int objY = (int) (c.getLocation().getY() + e.getPoint().getY() - origen.getY());
                    c.setLocation(objX, objY);
                    JPanel p = (JPanel) c.getParent();
                    if (!PintaControl.getInstance().isMouseWithinComponent()) {
                        TransferHandler handler = labelArrastrada.getTransferHandler(); // ImageLabelTH
                        handler.exportAsDrag(labelArrastrada, e, TransferHandler.MOVE);
                        /**
                         * Cuando el ratón sale del panel pasando por encima de
                         * otra label algunas veces no exporta bien el
                         * transferable, para asegurar que se exporta en todas
                         * las ocasiones, exportamos si el ratón se arrastra
                         * fuera del panel. Si no se usa la referencia
                         * "labelArrastrada" para esta finalidad, al arrastrar
                         * fuera por encima de otra label el sistema hace la
                         * exportación de la label por la que se pasó por
                         * encima, no de la que estamos arrastrando, con
                         * labelArrastrada nos aseguramos que exportamos la
                         * label correcta.
                         */
                        System.out.println("exported desde mouse dragged por punto: " + e.getPoint());
                        pulsado = false;
                        labelArrastrada = null;
                    }
                    p.setComponentZOrder(c, 0);
                    sDel.setVisible(true);
                    /**
                     * Actualizar posicion en la db
                     */
                    switch (checkTipoLabel(c)) {
                        case Arasaac:
                            int lmov = dbpcts.actualizaPosicionPicto(getLabelDbId(c), objX, objY);
                            //System.out.println("se han movido " + lmov + " pictogramas a coordenadas: " + objX + ", " + objY);
                            break;
                        case Flecha:
                            int fmov = dbflchs.actualizaPosicionFlecha(getLabelDbId(c), objX, objY);
                            //System.out.println("se han movido " + fmov + " flechas a coordenadas: " + objX + ", " + objY);
                            break;
                        default:
                            int bmov = dbbcts.actualizaPosicionBocadillo(getLabelDbId(c), objX, objY);
                            //System.out.println("se han movido " + bmov + "bocadillos a coordenadas: " + objX + ", " + objY);
                            break;
                    }
                    // Registrar orden de las Zs
                    JPanel panel = PictoController.getContenetor(mborrar).obtenCanvas();
                    Zordenante.setOrdenZs(panel);
                    current.giveComposicionFeedback(current.complisteners, p, "", idCompos);
                } else { // o pintar la flecha
                    PintaControl.getInstance().ratonDragged(mlabel);
                }
            }
        };
        mlabel.addMouseMotionListener(mmL);

        ImageLabelTH handler = new ImageLabelTH(mlabel, mborrar, idCompos);
        handler.addMyFeedbackListener((MyFeedbackListener<JLabel>) (tipo, feedback) -> {
            switch (tipo) {
                case ImageLabelTH.TIPO_DROP_BORRAR:
                    current.giveComposicionFeedback(current.complisteners, (JPanel) mlabel.getParent(), "", idCompos);
                    break;
            }
        });
        mlabel.setTransferHandler(handler);
        return mlabel;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // La JLabel se elimina del JPanel desde el handler de la propia JLabel => ImageLabelTH
        super.exportDone(source, data, action);
        mDel.setVisible(false);
    }

    public static void setLabelDbId(JLabel jl, int id) {
        jl.setName(Integer.toString(id));
    }

    public static void setLabelArasaacId(JLabel jl, String str) {
        jl.setText(str);
    }

    public static int getLabelDbId(JLabel jl) {
        return Integer.parseInt(jl.getName());
    }

    public static String getLabelArasaacId(JLabel jl) {
        return jl.getText();
    }

    @Override
    public void setOrdenZsEnDb(JPanel panel) {
        Zordenante.setOrdenZs(panel);
    }

    @Override
    public void addComposicionFeedbackListener(ComposicionFeedbackListener toAdd) {
        complisteners.add(toAdd);
    }

    @Override
    public void giveComposicionFeedback(List<ComposicionFeedbackListener> listeners, JPanel panel, String titulo, int id) {
        listeners.forEach((listener) -> {
            listener.dandoFeedback(panel, titulo, id);
        });
    }

    public static enum TipoLabel {
        Arasaac(""), Flecha(FLECHA), Bocadillo(BOCADILLO), Nube(NUBE), Desconocido("");

        String txt;

        TipoLabel(String t) {
            txt = t;
        }

        public String texto() {
            return txt;
        }
    }

    public static TipoLabel checkTipoLabel(JLabel jl) {
        String id = getLabelArasaacId(jl);
        boolean arasaac = ArchivosArasaac.getInstance().localExists(getLabelArasaacId(jl));
        if (arasaac) {
            return TipoLabel.Arasaac;
        } else if (id.startsWith(FLECHA)) {
            return TipoLabel.Flecha;
        } else if (id.startsWith(BOCADILLO)) {
            return TipoLabel.Bocadillo;
        } else if (id.startsWith(NUBE)) {
            return TipoLabel.Nube;
        } else {
            return TipoLabel.Desconocido;
        }
    }

    public static TipoLabel checkTipoLabel(String str) {
        switch (str) {
            case FLECHA:
                return TipoLabel.Flecha;
            case NUBE:
                return TipoLabel.Nube;
            case BOCADILLO:
                return TipoLabel.Bocadillo;
            default:
                return TipoLabel.Arasaac;
        }
    }
}
