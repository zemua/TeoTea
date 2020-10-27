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
import teoytea.models.DrawPaneTH;
import teoytea.models.dibujos.BocadilloTH;
import teoytea.models.dibujos.ColorEnum;

/**
 *
 * @author miguel
 */
public class DBbocadillos {

    Db dbInstance;

    public DBbocadillos() {
        dbInstance = Db.getInstance();
    }

    ResultSet getAllBocadillos(int idcomp) {
        PreparedStatement stm;
        ResultSet rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("SELECT * FROM BOCADILLOS WHERE COMPOSICION=?");
            stm.setInt(1, idcomp);
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }

    public ResultSet getBocadillo(int id) {
        PreparedStatement stm;
        ResultSet rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("SELECT * FROM BOCADILLOS WHERE ID=?");
            stm.setInt(1, id);
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBbocadillos.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }

    public int deleteAllBocadillos(int idcomp) {
        PreparedStatement stm;
        int rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("DELETE FROM BOCADILLOS WHERE COMPOSICION = ?");
            stm.setInt(1, idcomp);
            rs = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            rs = -1;
        }
        return rs;
    }

    public int borrarBocadilloDeComp(int id) {
        PreparedStatement stm;
        try {
            stm = dbInstance.getConnection().prepareStatement("DELETE FROM BOCADILLOS WHERE ID = ?");
            stm.setInt(1, id);
            int del = stm.executeUpdate();
            return del;
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public Long anhadirBocadilloToComp(int comp, String tipo, String texto, int x, int y, ColorEnum.Cenum color) {
        PreparedStatement stm;
        try {
            stm = dbInstance.getConnection().prepareStatement("INSERT INTO BOCADILLOS (COMPOSICION, TIPO, TEXTO, X, Y, COLOR) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, comp);
            stm.setString(2, tipo);
            stm.setString(3, texto);
            stm.setInt(4, x);
            stm.setInt(5, y);
            stm.setInt(6, color.num());
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

    public int actualizaPosicionBocadillo(int id, int x, int y) {
        PreparedStatement stm;
        int res;
        try {
            stm = dbInstance.getConnection().prepareStatement("UPDATE BOCADILLOS SET X=?, Y=? WHERE ID=?");
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
            ResultSet rs = getAllBocadillos(idComp);
            while (rs.next()) {
                int dbid = rs.getInt("ID");
                String dbtipo = rs.getString("TIPO");
                String dbtexto = rs.getString("TEXTO");
                int dbx = rs.getInt("X");
                int dby = rs.getInt("Y");
                int dbcolor = rs.getInt("COLOR");

                ColorEnum.Cenum mcolor = ColorEnum.getCenumFromNum(dbcolor);
                String[] mtexto = dbtexto.split("\n");
                DrawPaneTH.TipoLabel mtipo;
                switch (dbtipo) {
                    case "globo":
                        mtipo = DrawPaneTH.TipoLabel.Nube;
                        break;
                    default:
                        mtipo = DrawPaneTH.TipoLabel.Bocadillo;
                        break;
                }

                BocadilloTH handler = new BocadilloTH();
                JLabel dblabel = handler.dataToLabel(dbid, mtipo, mtexto, dbx, dby, mcolor);

                panel.add(dblabel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
