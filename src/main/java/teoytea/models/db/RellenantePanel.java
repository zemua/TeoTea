/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import teoytea.controllers.PictoController;
import teoytea.interfaces.Borrante;
import teoytea.interfaces.PanelFiller;
import teoytea.models.ArchivosModel;
import teoytea.models.DrawPaneTH;
import teoytea.models.arasaac.ArchivosArasaac;
import teoytea.models.dibujos.BocadilloTH;
import teoytea.models.dibujos.ColorEnum;
import teoytea.models.dibujos.Pintamelo;

/**
 *
 * @author miguel
 */
public class RellenantePanel implements PanelFiller {

    DBPictogramas dbpcts;
    DBFlechas dbflchs;
    DBbocadillos dbbcts;
    JPanel panel;
    int idcomp;

    public RellenantePanel(JPanel panel, int idcomp) {
        dbpcts = new DBPictogramas();
        dbflchs = new DBFlechas();
        dbbcts = new DBbocadillos();
        this.panel = panel;
        this.idcomp = idcomp;
    }

    @Override
    public void rellenarPanel(JPanel panel, int idcomp) {
        try {
            Borrante borrante = PictoController.getBorrante(panel);
            JButton mDel = borrante.getElementoBorrante();
            // PICTOGRAMAS
            ResultSet rst = dbpcts.getAllPictos(idcomp);
            while (rst.next()) {
                JLabel jl = resultSetToArasaacLabel(rst);
                DrawPaneTH.adaptarLabelTh(jl, mDel, idcomp);
                Pintamelo.labelToPanel(jl, panel);
            }
            // FLECHAS
            rst = dbflchs.getAllFlechas(idcomp);
            while (rst.next()) {
                JLabel jl = resultSetToFlechaLabel(rst);
                DrawPaneTH.adaptarLabelTh(jl, mDel, idcomp);
                Pintamelo.labelToPanel(jl, panel);
            }
            // BOCADILLOS
            rst = dbbcts.getAllBocadillos(idcomp);
            while (rst.next()){
                JLabel jl = resultSetToBocadillo(rst);
                DrawPaneTH.adaptarLabelTh(jl, mDel, idcomp);
                Pintamelo.labelToPanel(jl, panel);
            }
            
            Zordenante.ordenaZs(panel);

        } catch (SQLException ex) {
            Logger.getLogger(RellenantePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rellenarPanel() {
        rellenarPanel(panel, idcomp);
    }

    private JLabel resultSetToArasaacLabel(ResultSet rs) {
        JLabel jl = new JLabel();
        try {
            int id = rs.getInt(1);
            int arasaacid = rs.getInt(3);
            int x = rs.getInt(4);
            int y = rs.getInt(5);
            int width = rs.getInt(6);
            int height = rs.getInt(7);
            System.out.println("set label db id: " + id);
            DrawPaneTH.setLabelDbId(jl, id);
            System.out.println("set label arasaac id: " + arasaacid);
            DrawPaneTH.setLabelArasaacId(jl, Integer.toString(arasaacid));
            System.out.println("set bounds: " + " x: " + x + " y: " + y + " width: " + width + " height: " + height);
            jl.setBounds(x, y, width, height);
            ImageIcon imic = new ImageIcon(ArchivosArasaac.getInstance().getLocalImage(DrawPaneTH.getLabelArasaacId(jl)));
            imic = ArchivosModel.scaleImage(imic, width, height);
            jl.setIcon(imic);
        } catch (SQLException ex) {
            Logger.getLogger(RellenantePanel.class.getName()).log(Level.SEVERE, null, ex);
            jl = null;
        }
        return jl;
    }
    
    private JLabel resultSetToFlechaLabel (ResultSet rs){
        JLabel jl;
        try {
            int id = rs.getInt(1);
            int xinicio = rs.getInt(3);
            int yinicio = rs.getInt(4);
            int xfinal = rs.getInt(5);
            int yfinal = rs.getInt(6);
            int x = rs.getInt(7);
            int y = rs.getInt(8);
            int color = rs.getInt(9);
            
            jl = Pintamelo.dataToLabel(id, xinicio, yinicio, xfinal, yfinal, x, y, ColorEnum.getCenumFromNum(color));
            System.out.println("set flecha db id: " + id);
            DrawPaneTH.setLabelDbId(jl, id);
            DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.FLECHA);
        } catch (SQLException ex) {
            Logger.getLogger(RellenantePanel.class.getName()).log(Level.SEVERE, null, ex);
            jl = null;
        }
        return jl;
    }
    
    private JLabel resultSetToBocadillo (ResultSet rs){
        JLabel jl;
        try{
            int id = rs.getInt(1);
            String tipo = rs.getString(3);
            String texto = rs.getString(4);
            int x = rs.getInt(5);
            int y = rs.getInt(6);
            int color = rs.getInt(7);
            
            BocadilloTH handler = new BocadilloTH();
            ColorEnum.Cenum mcenum = ColorEnum.getCenumFromNum(color);
            jl = handler.dataToLabel(id, DrawPaneTH.checkTipoLabel(tipo), texto.split("\n"), x, y, mcenum);
            System.out.println("set " + DrawPaneTH.checkTipoLabel(tipo).texto() + " db id: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(RellenantePanel.class.getName()).log(Level.SEVERE, null, ex);
            jl = null;
        }
        return jl;
    }
}
