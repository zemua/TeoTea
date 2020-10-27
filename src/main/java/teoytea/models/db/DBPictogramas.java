/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import teoytea.models.arasaac.ArchivosArasaac;

/**
 *
 * @author miguel
 */
public class DBPictogramas {
    
    Db dbInstance;
    JPanel panel;
    int composicion;
    
    public DBPictogramas(JPanel panel, int composicion){
        dbInstance = getDbInstance();
        this.panel = panel;
        this.composicion = composicion;
    }
    
    public DBPictogramas(){
        dbInstance = getDbInstance();
        this.panel = null;
        this.composicion = -1;
    }
    
    private Db getDbInstance(){
        return Db.getInstance();
    }
    
    private JPanel getPanel(){
        return panel;
    }
    
    private int getIdComposicion(){
        return composicion;
    }
    
    private void setPanel(JPanel mpane){
        panel = mpane;
    }
    
    private void setIdComposicion(int i){
        composicion = i;
    }
    
    public ResultSet getAllPictos(int idcomp){
        PreparedStatement stm;
        ResultSet rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("SELECT * FROM PICTOGRAMAS WHERE COMPOSICION=?");
            stm.setInt(1, idcomp);
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }
    
    ResultSet getAllPictos(){
        return getAllPictos(composicion);
    }
    
    public int deleteAllPictos(int idcomp){
        PreparedStatement stm;
        int rs;
        try {
            stm = dbInstance.getConnection().prepareStatement("DELETE FROM PICTOGRAMAS WHERE COMPOSICION = ?");
            stm.setInt(1, idcomp);
            rs = stm.executeUpdate();
        }
        catch (SQLException ex){
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            rs = -1;
        }
        return rs;
    }
    
    public int borrarPictoDeComp(int id){
            PreparedStatement stm;
            try{
                stm = dbInstance.getConnection().prepareStatement("DELETE FROM PICTOGRAMAS WHERE ID = ?");
                stm.setInt(1, id);
                int del = stm.executeUpdate();
                return del;
            }
            catch (SQLException ex){
                Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
    }
    
    public Long anhadirPictoToComp(int comp, int arasaac, int x, int y, int width, int height){
        PreparedStatement stm;
        try{
            stm = dbInstance.getConnection().prepareStatement("INSERT INTO PICTOGRAMAS (COMPOSICION, CODIGO, X, Y, WIDTH, HEIGHT) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, comp);
            stm.setInt(2, arasaac);
            stm.setInt(3, x);
            stm.setInt(4, y);
            stm.setInt(5, width);
            stm.setInt(6, height);
            stm.executeUpdate();
            ResultSet generatedId = stm.getGeneratedKeys();
                if(generatedId.next()){
                    return generatedId.getLong(1);
                }
                return null;
        }
        catch (SQLException ex){
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public int actualizaPosicionPicto(int id, int x, int y){
        PreparedStatement stm;
        int res;
        try{
            stm = dbInstance.getConnection().prepareStatement("UPDATE PICTOGRAMAS SET X=?, Y=? WHERE ID=?");
            stm.setInt(1, x);
            stm.setInt(2, y);
            stm.setInt(3, id);
            res = stm.executeUpdate();
        }catch (SQLException ex){
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            res = -1;
        }
        return res;
    }
        
    public void fillPanel(JPanel panel){
        try {
            ResultSet rs = getAllPictos();
            while (rs.next()){
                int dbid = rs.getInt("ID");
                int dbcodigo = rs.getInt("CODIGO"); // de arasaac
                int dbx = rs.getInt("X");
                int dby = rs.getInt("Y");
                
                JLabel dblabel = new JLabel();
                dblabel.setText(Integer.toString(dbid));
                dblabel.setBounds(dbx, dby, dblabel.getWidth(), dblabel.getHeight());
                BufferedImage bi = ArchivosArasaac.getInstance().getLocalImage(dbcodigo);
                dblabel.setIcon(new ImageIcon(bi));
                
                panel.add(dblabel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBPictogramas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillPanel(){
        fillPanel(this.panel);
    }
}
