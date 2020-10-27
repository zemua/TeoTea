/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;
import teoytea.interfaces.ZsOrdenSetter;
import teoytea.models.db.DBFlechas;
import teoytea.models.db.DBPictogramas;
import teoytea.models.db.DBbocadillos;
import teoytea.models.db.Zordenante;

/**
 *
 * @author miguel
 */
public class ImageLabelTH extends TransferHandler implements Transferable, ZsOrdenSetter, MyFeedbacker<JLabel> {

    private static final DataFlavor[] FLAVORS = {imageFlavor};
    private final JLabel mLabel;
    private final JButton mDel;
    private final int idComposicion;
    
    public static final int TIPO_DROP_BORRAR = 0;
    
    ArrayList<MyFeedbackListener<JLabel>> feedbacklisteners = new ArrayList<>();

    public ImageLabelTH(JLabel l, JButton del, int idComposicion) {
        mLabel = l;
        mDel = del;
        this.idComposicion = idComposicion;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        //System.out.println("terminando exportDone de ImageLabelTH");
        super.exportDone(source, data, action);
        if (action == MOVE) {
            JPanel mPanel = (JPanel) mLabel.getParent();
            mPanel.remove(mLabel);
            mPanel.revalidate();
            mPanel.repaint();
            giveMyFeedback(TIPO_DROP_BORRAR, mLabel);

            /**
             * Quitar de la db
             */
            switch (DrawPaneTH.checkTipoLabel(mLabel)) {
                case Arasaac:
                    DBPictogramas dbpcts = new DBPictogramas(mPanel, this.idComposicion);
                    int bi = dbpcts.borrarPictoDeComp(DrawPaneTH.getLabelDbId(mLabel));
                    System.out.println("se han borrado de la db " + bi + " registros");
                    break;
                case Flecha:
                    DBFlechas dbflchs = new DBFlechas();
                    int ci = dbflchs.borrarFlechaDeComp(DrawPaneTH.getLabelDbId(mLabel));
                    System.out.println("se han borrado de la db " + ci + " registros");
                default:
                    DBbocadillos dbbcts = new DBbocadillos();
                    int ni = dbbcts.borrarBocadilloDeComp(DrawPaneTH.getLabelDbId(mLabel));
                    System.out.println("se han borrado de la db " + ni + " registros");
            }
            setOrdenZsEnDb(mPanel);
        }
        mDel.setVisible(false);
    }

    @Override
    public int getSourceActions(JComponent c) {
        //System.out.println("get source actions de ImageLabelTH");
        return TransferHandler.MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        //System.out.println("puede ImageLabelTH importar esto?");
        if (!support.isDataFlavorSupported(FLAVORS[0]) || !support.getComponent().getParent().getName().equals("miCanvas")) {
            System.out.println("no");
            return false;
        }
        //System.out.println("si");
        return true;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }
        JPanel jp = (JPanel) support.getComponent().getParent();
        return DrawPaneTH.attachLabelEnPanel(jp, mDel, support, idComposicion);
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        //System.out.println("create transferable de ImageLabelTH from: " + comp.toString());
        mDel.setVisible(true);
        return this;
    }

    /*
    Implementación de la parte del transferable
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        //System.out.println("is data flavor supported en ImageLabelTH?");
        return FLAVORS[0].equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor mFlavor) {
        //System.out.println("get transfer data de ImageLabelTH");
        if (isDataFlavorSupported(mFlavor)) {
            //return mLabel.getIcon();
            return mLabel;
        }
        return null;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        //System.out.println("get Transfer Data Flavors de ImageLabelTH");
        return FLAVORS;
    }

    @Override
    public void setOrdenZsEnDb(JPanel panel) {
        Zordenante.setOrdenZs(panel);
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener<JLabel> toAdd) {
        feedbacklisteners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, JLabel feedback) {
        feedbacklisteners.forEach((listener)->{
            listener.giveMyFeedback(tipo, feedback);
        });
    }

}
