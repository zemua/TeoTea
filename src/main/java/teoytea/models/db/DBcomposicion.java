/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class DBcomposicion {
    
    Db dbInstance = null;

    public int actualizaComposicion(String nombre, int id) {

        PreparedStatement stm;
        int res;
        try {
            stm = getDbInstance().getConnection().prepareStatement("UPDATE COMPOSICIONES SET NOMBRE=? WHERE ID=?");
            stm.setString(1, nombre);
            stm.setInt(2, id);
            res = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }
        return res;
    }

    public String getNombre(int id) {
        PreparedStatement stm;
        ResultSet rs;
        String titulo = "";
        try {
            stm = getDbInstance().getConnection().prepareStatement("SELECT * FROM COMPOSICIONES WHERE ID=?");
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()){
                titulo = rs.getString("NOMBRE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titulo;
    }

    private Db getDbInstance() {
        if (dbInstance == null) {
            dbInstance = Db.getInstance();
        }
        return dbInstance;
    }
    
    public int borrarComp(int id) {
        PreparedStatement stm;
        try {
            stm = getDbInstance().getConnection().prepareStatement("DELETE FROM COMPOSICIONES WHERE ID = ?");
            stm.setInt(1, id);
            int del = stm.executeUpdate();
            stm = getDbInstance().getConnection().prepareStatement("DELETE FROM PICTOGRAMAS WHERE COMPOSICION = ?");
            stm.setInt(1, id);
            int del2 = stm.executeUpdate();
            return del + del2;
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    } 
}
