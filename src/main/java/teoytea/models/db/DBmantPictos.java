/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.models.arasaac.ArchivosArasaac;
import teoytea.models.arasaac.FetchImageWorker;
import teoytea.models.arasaac.SimpleImageFetcher;

/**
 *
 * @author miguel
 */
public class DBmantPictos {
    
    Db mydb;
    
    public DBmantPictos(){
        mydb = Db.getInstance();
    }
    
    private ArrayList<Integer> listaRegistrados(){
        ArrayList<Integer> registrados = new ArrayList<>();
        ResultSet rs;
        PreparedStatement stm;
        try {
            stm = Db.getInstance().getConnection().prepareStatement("SELECT DISTINCT CODIGO FROM PICTOGRAMAS");
            rs = stm.executeQuery();
            while(rs.next()){
                registrados.add(rs.getInt("CODIGO"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        
        return registrados;
    }
    
    public void descargaFaltantes(){
        ArchivosArasaac araid = ArchivosArasaac.getInstance();
        ArrayList<Integer> registrados = listaRegistrados();
        registrados.forEach((registrado)->{
            if(!araid.localExists(registrado)){
                URL url = SimpleImageFetcher.arasaacUrlFromId(registrado);
                FetchImageWorker t = new FetchImageWorker(url, registrado);
                t.addMyFeedbackListener((MyFeedbackListener<JLabel>)(tipo, feedback)->{
                    switch (tipo){
                        case FetchImageWorker.TIPO_ADD:
                            System.out.println("Descargado pictograma faltante: " + registrado);
                            break;
                    }
                });
                t.execute();
            }
        });
    }
    
}
