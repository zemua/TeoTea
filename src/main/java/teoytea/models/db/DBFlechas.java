/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import teoytea.models.dibujos.ColorEnum;
import teoytea.models.dibujos.Pintamelo;

/**
 *
 * @author miguel
 */
public class DBFlechas {

    Db dbInstance;

    public DBFlechas() {
        dbInstance = Db.getInstance();
    }

    ResultSet getAllFlechas(int idcomp) {
        PreparedStatement stm;
        ResultSet rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("SELECT * FROM FLECHAS WHERE COMPOSICION=?");
            stm.setInt(1, idcomp);
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }

    public ResultSet getUnaFlecha(int id) {
        PreparedStatement stm;
        ResultSet rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("SELECT * FROM FLECHAS WHERE ID=?");
            stm.setInt(1, id);
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }

    public int deleteAllFlechas(int idcomp) {
        PreparedStatement stm;
        int rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("DELETE FROM FLECHAS WHERE COMPOSICION = ?");
            stm.setInt(1, idcomp);
            rs = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            rs = -1;
        }
        return rs;
    }

    public int borrarFlechaDeComp(int id) {
        PreparedStatement stm;
        try {
            stm = dbInstance.getConnection().prepareStatement("DELETE FROM FLECHAS WHERE ID = ?");
            stm.setInt(1, id);
            int del = stm.executeUpdate();
            return del;
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /*
    Se añaden dentro de PintaControl, en ratonReleased
     */
    public Long anhadirFlechaToComp(int comp, int x_inicio, int y_inicio, int x_final, int y_final, int x, int y, ColorEnum.Cenum color) {
        PreparedStatement stm;
        try {
            stm = dbInstance.getConnection().prepareStatement("INSERT INTO FLECHAS (COMPOSICION, X_INICIO, Y_INICIO, X_FINAL, Y_FINAL, X, Y, COLOR) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, comp);
            stm.setInt(2, x_inicio);
            stm.setInt(3, y_inicio);
            stm.setInt(4, x_final);
            stm.setInt(5, y_final);
            stm.setInt(6, x);
            stm.setInt(7, y);
            stm.setInt(8, color.num());
            stm.executeUpdate();
            ResultSet generatedId = stm.getGeneratedKeys();
            if (generatedId.next()) {
                return generatedId.getLong(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Long duplicate(int id, int mx, int my) {
        PreparedStatement stm;
        ResultSet rs;

        try {
            stm = dbInstance.getConnection().prepareStatement("SELECT * FROM FLECHAS WHERE ID=?");
            stm.setInt(1, id);
            rs = stm.executeQuery();

            rs.next();
            int comp = rs.getInt("COMPOSICION");
            int x_inicio = rs.getInt("X_INICIO");
            int y_inicio = rs.getInt("Y_INICIO");
            int x_final = rs.getInt("X_FINAL");
            int y_final = rs.getInt("Y_FINAL");
            int x = mx;
            int y = my;
            int color = rs.getInt("COLOR");

            stm = dbInstance.getConnection().prepareStatement("INSERT INTO FLECHAS (COMPOSICION, X_INICIO, Y_INICIO, X_FINAL, Y_FINAL, X, Y, COLOR) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, comp);
            stm.setInt(2, x_inicio);
            stm.setInt(3, y_inicio);
            stm.setInt(4, x_final);
            stm.setInt(5, y_final);
            stm.setInt(6, x);
            stm.setInt(7, y);
            stm.setInt(8, color);
            stm.executeUpdate();
            ResultSet generatedId = stm.getGeneratedKeys();
            if (generatedId.next()) {
                return generatedId.getLong(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int actualizaPosicionFlecha(int id, int x, int y) {
        PreparedStatement stm;
        int res;
        try {
            stm = dbInstance.getConnection().prepareStatement("UPDATE FLECHAS SET X=?, Y=? WHERE ID=?");
            stm.setInt(1, x);
            stm.setInt(2, y);
            stm.setInt(3, id);
            res = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }
        return res;
    }

    public void fillPanel(JPanel panel, int idComp) {
        try {
            ResultSet rs = getAllFlechas(idComp);
            while (rs.next()) {
                int dbid = rs.getInt("ID");
                int dbXinicio = rs.getInt("X_INICIO");
                int dbYinicio = rs.getInt("Y_INIXIO");
                int dbXfinal = rs.getInt("X_FINAL");
                int dbYfinal = rs.getInt("Y_FINAL");
                int dbx = rs.getInt("X");
                int dby = rs.getInt("Y");
                int dbcolor = rs.getInt("COLOR");

                ColorEnum.Cenum mcolor = ColorEnum.getCenumFromNum(dbcolor);
                JLabel dblabel = Pintamelo.dataToLabel(dbid, dbXinicio, dbYinicio, dbXfinal, dbYfinal, dbx, dby, mcolor);

                panel.add(dblabel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
