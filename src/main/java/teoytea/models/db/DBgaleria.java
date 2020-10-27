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

/**
 *
 * @author miguel
 */
public class DBgaleria {
    
    /**
     *  testado junto con DBcomposicion en DBcomposicionTest
     */
    
    private static DBgaleria instance = null;
    
    private DBgaleria(){
    }
    
    public static DBgaleria getInstance(){
        if (instance == null){
            instance = new DBgaleria();
        }
        return instance;
    }
    
    private Db getDb(){
        return Db.getInstance();
    }
    
    public long createNewDibujo(){
        PreparedStatement stm;
        try {
            stm = getDb().getConnection().prepareStatement("INSERT INTO COMPOSICIONES (NOMBRE) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "Nuevo Dibujo");
            stm.executeUpdate();
            ResultSet generatedId = stm.getGeneratedKeys();
                if(generatedId.next()){
                    return generatedId.getLong(1);
                }
                return -1;
        } catch (SQLException ex) {
            Logger.getLogger(DBgaleria.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public ResultSet getAllDibujos(){
        PreparedStatement stm;
        ResultSet rs;
        try {
            stm = getDb().getConnection().prepareStatement("SELECT * FROM COMPOSICIONES");
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBgaleria.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }
}
