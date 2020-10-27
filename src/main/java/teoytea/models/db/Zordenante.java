/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import teoytea.models.DrawPaneTH;

/**
 *
 * @author miguel
 */
public class Zordenante {

    public static void setOrdenZs(JPanel panel) {
        Component[] comps = panel.getComponents();
        for (Component comp : comps) {
            if (comp instanceof JLabel) {
                int orden = panel.getComponentZOrder(comp);
                int id = DrawPaneTH.getLabelDbId((JLabel) comp);
                DrawPaneTH.TipoLabel tipo = DrawPaneTH.checkTipoLabel((JLabel) comp);
                actualizaZ(id, orden, tipo);
            }
        }
    }
    
    static void ordenaZs(JPanel panel){
        Component[] comps = panel.getComponents();
        for (Component comp : comps) {
            if (comp instanceof JLabel) {
                int id = DrawPaneTH.getLabelDbId((JLabel) comp);
                DrawPaneTH.TipoLabel tipo = DrawPaneTH.checkTipoLabel((JLabel) comp);
                int orden = getZdeId(id, tipo);
                if (orden >= panel.getComponentCount()){
                    orden = panel.getComponentCount()-1;
                    System.out.println("Un label está fuera del rango Z");
                }
                panel.setComponentZOrder(comp, orden);
            }
        }
    }
    
    private static int getZdeId(int id, DrawPaneTH.TipoLabel tipo){
        PreparedStatement stm;
        int res = -1;
        try{
            Db dbInstance = Db.getInstance();
            switch(tipo){
                case Arasaac:
                    stm = dbInstance.getConnection().prepareStatement("SELECT * FROM PICTOGRAMAS WHERE ID = ?");
                    stm.setInt(1, id);
                    ResultSet set = stm.executeQuery();
                    if (set.next()){
                        res = set.getInt("ZORDER");
                    }
                    break;
                case Flecha:
                    stm = dbInstance.getConnection().prepareStatement("SELECT * FROM FLECHAS WHERE ID = ?");
                    stm.setInt(1, id);
                    set = stm.executeQuery();
                    if (set.next()){
                        res = set.getInt("ZORDER");
                    }
                    break;
                default:
                    stm = dbInstance.getConnection().prepareStatement("SELECT * FROM BOCADILLOS WHERE ID = ?");
                    stm.setInt(1, id);
                    set = stm.executeQuery();
                    if (set.next()){
                        res = set.getInt("ZORDER");
                    }
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Zordenante.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }
        return res;
    }

    private static int actualizaZ(int id, int z, DrawPaneTH.TipoLabel tipo) {
        PreparedStatement stm;
        int res;
        try {
            Db dbInstance = Db.getInstance();
            switch (tipo) {
                case Arasaac:
                    stm = dbInstance.getConnection().prepareStatement("UPDATE PICTOGRAMAS SET ZORDER=? WHERE ID=?");
                    stm.setInt(1, z);
                    stm.setInt(2, id);
                    res = stm.executeUpdate();
                    break;
                case Flecha:
                    stm = dbInstance.getConnection().prepareStatement("UPDATE FLECHAS SET ZORDER=? WHERE ID=?");
                    stm.setInt(1, z);
                    stm.setInt(2, id);
                    res = stm.executeUpdate();
                    break;
                default:
                    stm = dbInstance.getConnection().prepareStatement("UPDATE BOCADILLOS SET ZORDER=? WHERE ID=?");
                    stm.setInt(1, z);
                    stm.setInt(2, id);
                    res = stm.executeUpdate();
                    break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }
        return res;
    }
}
