/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.interfaces;

import teoytea.controllers.PictoController;
import teoytea.models.ArchivosModel;

/**
 *
 * @author miguel
 */
public interface ControladorInterface {
    public PictoController.Accion eventoBoton(PictoController.Accion accion, int id);
    public ArchivosModel getAm();
}
