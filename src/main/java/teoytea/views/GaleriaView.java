/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.views;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import teoytea.interfaces.ControladorInterface;
import teoytea.interfaces.EsGaleria;
import teoytea.interfaces.FeedbackGiver;
import teoytea.interfaces.FeedbackListener;
import teoytea.models.db.DBgaleria;

/**
 *
 * @author miguel
 */
public class GaleriaView extends javax.swing.JPanel implements EsGaleria, FeedbackGiver<Integer> {

    ControladorInterface controlador;
    List<FeedbackListener> listeners = new ArrayList<>();

    /**
     * Creates new form GaleriaView
     */
    public GaleriaView() {
        initComponents();
        setVisible(true);
    }

    @Override
    public void setController(ControladorInterface c) {
        controlador = c;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        canvasPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        trashButon = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(100, 100));

        canvasPanel.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                canvasPanelComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                canvasPanelComponentRemoved(evt);
            }
        });
        canvasPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                canvasPanelComponentMoved(evt);
            }
        });
        canvasPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                canvasPanelPropertyChange(evt);
            }
        });
        canvasPanel.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane1.setViewportView(canvasPanel);

        addButton.setText("+");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        trashButon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/recycle.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 318, Short.MAX_VALUE)
                        .addComponent(trashButon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addButton))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(trashButon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        int id = (int) DBgaleria.getInstance().createNewDibujo();
        System.out.println("Nueva composición con id: " + id);
        //controlador.eventoBoton(PictoController.Accion.Add, id);
        giveFeedback(listeners, id);
    }//GEN-LAST:event_addButtonActionPerformed

    private void canvasPanelComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_canvasPanelComponentAdded
        apanharLayout(canvasPanel);
    }//GEN-LAST:event_canvasPanelComponentAdded

    private void canvasPanelComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_canvasPanelComponentRemoved
        apanharLayout(canvasPanel);
    }//GEN-LAST:event_canvasPanelComponentRemoved

    private void canvasPanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_canvasPanelPropertyChange

    }//GEN-LAST:event_canvasPanelPropertyChange

    private void canvasPanelComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_canvasPanelComponentMoved

    }//GEN-LAST:event_canvasPanelComponentMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel canvasPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton trashButon;
    // End of variables declaration//GEN-END:variables

    @Override
    public JPanel getCanvas() {
        return canvasPanel;
    }

    private void apanharLayout(JPanel panel) {
        int anchoCelda = 150; // ancho mínimo de la celda para calcular columnas
        int elementos = panel.getComponentCount();
        int width = panel.getWidth();
        int columnas = width / anchoCelda;
        if (columnas > 3) {
            columnas = 3;
        }
        int filas = elementos / columnas;
        if (elementos % columnas > 0) {
            filas += 1;
        }
        GridLayout layout = new GridLayout(filas, columnas);
        panel.setLayout(layout);
    }

    @Override
    public void addFeedbackListener(FeedbackListener toAdd) {
        listeners.add(toAdd);
    }

    @Override
    public void giveFeedback(List<FeedbackListener> listeners, Integer feedback) {
        listeners.forEach((listener) -> {
            System.out.println("dando feedback a " + listener + " con info " + feedback);
            listener.dandoFeedback(feedback);
        });
    }
    
    public JButton getTrash(){
        return trashButon;
    }
}
