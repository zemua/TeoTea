/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.controllers;

import java.awt.Component;
import java.awt.Toolkit;
import java.io.File;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import teoytea.interfaces.Borrante;
import teoytea.interfaces.ComposicionFeedbackGiver;
import teoytea.interfaces.ComposicionFeedbackObserver;
import teoytea.interfaces.ContenedorCanvas;
import teoytea.interfaces.ControladorInterface;
import teoytea.interfaces.EsImgRenderer;
import teoytea.interfaces.FeedbackGiver;
import teoytea.interfaces.FeedbackListener;
import teoytea.interfaces.FeedbackObserver;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.TextoDropable;
import teoytea.models.ArchivosModel;
import teoytea.models.BorrarTH;
import teoytea.models.CompImager;
import teoytea.models.DragImageList;
import teoytea.models.DrawPaneTH;
import teoytea.models.arasaac.SimpleAraRest;
import teoytea.models.db.DBcomposicion;
import teoytea.models.db.DBmantPictos;
import teoytea.models.db.RellenantePanel;
import teoytea.models.dibujos.ArchivosComposicion;
import teoytea.models.dibujos.PintaControl;
import teoytea.views.BaseView;
import teoytea.views.DibujarView;
import teoytea.views.GaleriaView;
import teoytea.views.MyImgListRender;

/**
 *
 * @author miguel
 */
public class PictoController implements ControladorInterface, EsImgRenderer, FeedbackObserver, ComposicionFeedbackObserver {

    //abre el JFrame
    private final JFrame mFrame = new BaseView();
    private JPanel mPanel;
    ArchivosModel am;
    DBcomposicion dbComp;
    int idComp = -1;

    public PictoController() {
        mFrame.setTitle("TEO&TEA");
        mFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(PictoController.this.getClass().getResource("/teoytea/resources/aux/teo.png")));
        am = new ArchivosModel();
        mPanel = new GaleriaView();
        updatePane(mPanel);
        descargaFaltantes();
        GaleriaController gc = new GaleriaController((GaleriaView) mPanel);
        anhadeFeedbackObserver(gc);
        gc.load();
        dbComp = new DBcomposicion();
    }

    @Override
    public final void anhadeFeedbackObserver(FeedbackGiver gc) {
        gc.addFeedbackListener((FeedbackListener<Integer>) (Integer feedback) -> {
            eventoBoton(Accion.Open, feedback);
        });
    }

    @Override
    public ArchivosModel getAm() {
        return am;
    }

    private <T extends JPanel> void updatePane(T qPanel) {
        mPanel = qPanel;
        mFrame.setContentPane(qPanel);
        mFrame.revalidate();
    }

    public JFrame getFrame() {
        return mFrame;
    }

    public JPanel getPanel() {
        return mPanel;
    }

    @Override
    public void anhadeComposicionFeedbackObserver(ComposicionFeedbackGiver fg) {
        fg.addComposicionFeedbackListener((panel, titulo, id) -> {
            String mtitle = ((DibujarView) mPanel).getTituloTextField().getText();
            dbComp.actualizaComposicion(mtitle, id);
            makeComposicionImage((DibujarView) mPanel, id);
        });
    }

    void colocaComposicionFeedbackObserver(DibujarView dv) {
        DrawPaneTH dpth = (DrawPaneTH) dv.getMyDrawPanel().getTransferHandler();
        anhadeComposicionFeedbackObserver(dpth);
        PintaControl instance = PintaControl.getInstance();
        anhadeComposicionFeedbackObserver(instance);
        anhadeComposicionFeedbackObserver(dv);
    }

    private void descargaFaltantes() {
        DBmantPictos mantenimiento = new DBmantPictos();
        mantenimiento.descargaFaltantes();
    }

    public static enum Accion {
        Add, Guarda, Open;
    }

    @Override
    public Accion eventoBoton(Accion accion, int id) {
        idComp = id;
        Accion suceso;
        switch (accion) {
            case Open:
                DragImageList.imageFiles.clear();
                DibujarView dv = new DibujarView(this, id);
                colocaComposicionFeedbackObserver(dv);
                dv.getTituloTextField().setText(dbComp.getNombre(id));
                addListenerToDibujar(dv);
                updatePane(dv);
                mFrame.setTitle("TEO&TEA");
                suceso = Accion.Open;
                break;
            case Guarda:
                updatePane(new GaleriaView());
                GaleriaController gc = new GaleriaController((GaleriaView) mPanel);
                anhadeFeedbackObserver(gc);
                gc.load();
                descargaFaltantes();
                suceso = Accion.Guarda;
                break;
            case Add:
                dv = new DibujarView(this, id);
                colocaComposicionFeedbackObserver(dv);
                dv.getTituloTextField().setText(dbComp.getNombre(id));
                addListenerToDibujar(dv);
                updatePane(dv);
                suceso = Accion.Add;
                break;
            default:
                System.out.println("se ha pulsado un botón sin instrucción asignada");
                suceso = null;
                break;
        }
        msgBoton(accion);
        return suceso;
    }

    private void msgBoton(Accion a) {
        System.out.println("se ha pulsado botón: " + a);
    }

    private void addListenerToDibujar(DibujarView dv) {
        dv.addMyFeedbackListener((i, component) -> {
            if (i == DibujarView.TIPO_ARASAAC_REST) { // si hemos introducido datos en consulta arasaac
                callArasaacRest(dv);
            }
        });
        // y añadimos al boton borrar para guardar la composicion
        ((BorrarTH) ((JButton) dv.getElementoBorrante()).getTransferHandler()).addMyFeedbackListener((MyFeedbackListener<BorrarTH>) (tipo, feedback) -> {
            switch (tipo) {
                case BorrarTH.TIPO_BORRADO:
                    String mtitle = ((DibujarView) mPanel).getTituloTextField().getText();
                    dbComp.actualizaComposicion(mtitle, idComp);
                    makeComposicionImage((DibujarView) mPanel, idComp);
                    break;
            }
        });
    }

    private void callArasaacRest(DibujarView dv) {
        SimpleAraRest sar = new SimpleAraRest();
        sar.addMyFeedbackListener((MyFeedbackListener<JLabel>) (int i, JLabel jl) -> {
            DefaultListModel model = (DefaultListModel) dv.getArasaacList().getModel();
            switch (i) {
                case SimpleAraRest.TIPO_ADD_LABEL: // si es añadir cosas que nos pasa del rest
                    System.out.println("tipo add label");
                    if (sar.esLaMasReciente()) {
                        model.addElement(jl);
                        System.out.println("add ejecutado");
                        dv.getArasaacList().repaint();
                        dv.getArasaacList().revalidate();
                    }
                    break;
                case SimpleAraRest.TIPO_ADD_ERROR: // si nos dice que hay un error
                    System.out.println("tipo add error");
                    //if (model.getSize() == 1 && model.get(0).getClass() == JLabel.class && ((JLabel) model.get(0)).getText().equals(SimpleAraRest.PLACEHOLDER)) {
                    //model.remove(0); // quitarmos placeholder de carga
                    //}
                    //if (model.getSize() == 0) {
                    //model.add(0, jl); // ponemos placeholder de error si no hay niguna otra etiqueta
                    //}
                    //dv.getArasaacList().repaint();
                    //dv.getArasaacList().revalidate();
                    break;
                case SimpleAraRest.TIPO_TIME_OUT: // si se ha acabado el tiempo y no ha empezado otro proceso
                    if (sar.esLaMasReciente()) {
                        System.out.println("tipo time out");
                        if (model.get(0) != null && model.get(0).getClass() == JLabel.class && ((JLabel) model.get(0)).getText().equals(SimpleAraRest.PLACEHOLDER)) {
                            if (sar.permisoParaLimpiar()) {
                                model.remove(0);
                                System.out.println("remove ejecutado");
                                if (model.size() == 0) {
                                    model.add(0, jl);
                                    System.out.println("add ejecutado");
                                }
                                dv.getArasaacList().repaint();
                                dv.getArasaacList().revalidate();
                            }
                        }
                    }
                    break;
                case SimpleAraRest.TIPO_CLEAR_LIST:
                    System.out.println("tipo clear list");
                    if (sar.esLaMasReciente()) {
                        model.clear();
                        sar.clearPermisoParaLimpiar();
                        System.out.println("clear ejecutado");
                    }
                    break;
                case SimpleAraRest.TIPO_REPAINT_LIST:
                    //System.out.println("tipo repaint list");
                    if (model.size() > 0 && ((JLabel) model.get(0)).getText().equals(SimpleAraRest.PLACEHOLDER)) {
                        dv.getArasaacList().repaint();
                        dv.getArasaacList().revalidate();
                        System.out.println("repintado ejecutado");
                    }
                    break;
                case SimpleAraRest.TIPO_LIMPIA_PLACEHOLDER:
                    System.out.println("tipo limpia placeholder");
                    if (model.get(0) != null && model.get(0).getClass() == JLabel.class && ((JLabel) model.get(0)).getText().equals(SimpleAraRest.PLACEHOLDER)) {
                        if (sar.permisoParaLimpiar()) {
                            model.remove(0);
                            System.out.println("remove ejecutado");
                            dv.getArasaacList().repaint();
                            dv.getArasaacList().revalidate();
                        }

                    }
                    break;
            }
        } // añadir listener al rest-asaraac
        );
        sar.vamos(dv.getArasaacSeachText().getText()); // iniciar el rest-arasaac
    }

    /*
    Méthodo para ponerle el renderer al componente que llame de vuelta
     */
    @Override
    public void ponerRenderer(JList c) {

        int width = c.getWidth();
        am.setWidth(width - 50);

        Map<String, ImageIcon> mMap = am.getImagePathMap(); // #A

        //sacar array de Keys del map para pasar como nombres a la lista
        Set<String> keys = mMap.keySet();
        String[] s = keys.toArray(new String[keys.size()]);

        //ponerle el renderer a la lista para que saque las imágenes11
        c.setCellRenderer(new MyImgListRender());

        //añadir elementos
        for (String a : s) {
            DefaultListModel model = (DefaultListModel) c.getModel();
            JLabel label = new JLabel();
            label.setIcon(mMap.get(a));
            label.setText(a);
            model.addElement(label);
        }
    }

    public static <T extends Component & Borrante> T getBorrante(Component c) {
        Component borrante = c;
        while (!compruebaImplementaInterface(Borrante.class, borrante.getClass()) && borrante.getParent() != null) {
            borrante = borrante.getParent();
        }
        if (compruebaImplementaInterface(Borrante.class, borrante.getClass())) {
            return (T) borrante;
        }
        return null;
    }

    public static <T extends Component & TextoDropable> T getTextoDropable(Component c) {
        Component dropable = c;
        while (!compruebaImplementaInterface(TextoDropable.class, dropable.getClass()) && dropable.getParent() != null) {
            dropable = dropable.getParent();
        }
        if (compruebaImplementaInterface(TextoDropable.class, dropable.getClass())) {
            return (T) dropable;
        }
        return null;
    }

    public static <T extends Component & ContenedorCanvas> T getContenetor(Component c) {
        Component contenedor = c;
        while (!compruebaImplementaInterface(ContenedorCanvas.class, contenedor.getClass()) && contenedor.getParent() != null) {
            contenedor = contenedor.getParent();
        }
        if (compruebaImplementaInterface(ContenedorCanvas.class, contenedor.getClass())) {
            return (T) contenedor;
        }
        return null;
    }

    private static boolean compruebaImplementaInterface(Class<?> mInterface, Class<?> mObject) {
        Class[] interfaces = mObject.getInterfaces();

        for (Class i : interfaces) {
            if (i.toString().equals(mInterface.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void rellenarComposicion(JPanel p, int idComp) {
        RellenantePanel rp = new RellenantePanel(p, idComp);
        rp.rellenarPanel();
    }

    ImageIcon makeComposicionImage(DibujarView vpanel, int compId) {
        ArchivosComposicion arc = new ArchivosComposicion(ArchivosModel.getResetedPath());
        File file = arc.getComposicionFile(compId);
        CompImager compi = new CompImager(vpanel.getMyDrawPanel(), file);
        return compi.createFilePeque();
    }
}
