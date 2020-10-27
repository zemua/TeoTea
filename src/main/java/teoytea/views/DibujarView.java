/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.views;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;
import teoytea.controllers.PictoController;
import teoytea.interfaces.Borrante;
import teoytea.interfaces.ComposicionFeedbackGiver;
import teoytea.interfaces.ComposicionFeedbackListener;
import teoytea.interfaces.ContenedorCanvas;
import teoytea.interfaces.ControladorInterface;
import teoytea.interfaces.EsImgRenderer;
import teoytea.interfaces.MyFeedbackListener;
import teoytea.interfaces.MyFeedbacker;
import teoytea.interfaces.TextoDropable;
import teoytea.models.AutoPrinter;
import teoytea.models.BorrarTH;
import teoytea.models.CompImager;
import teoytea.models.CompPdfer;
import teoytea.models.DragImageList;
import teoytea.models.DrawPaneTH;
import teoytea.models.arasaac.ArasaacTH;
import teoytea.models.arasaac.ViewRender;
import teoytea.models.dibujos.BocadilloTH;
import teoytea.models.dibujos.PintaControl;
import teoytea.models.dibujos.Pintamelo;
import teoytea.models.dibujos.ColorEnum;

/**
 *
 * @author miguel
 * @param <C>
 */
public class DibujarView<C extends ControladorInterface & EsImgRenderer> extends javax.swing.JPanel implements Borrante, TextoDropable, ContenedorCanvas, ComposicionFeedbackGiver, MyFeedbacker<Component> {

    C controlador;
    boolean drawn = false;
    DefaultListModel model = new DefaultListModel();
    DefaultListModel model2 = new DefaultListModel();
    PintaControl pc;
    int idComposicion;
    List<ComposicionFeedbackListener> complisteners = new ArrayList<>();
    List<MyFeedbackListener<Component>> mylisteners = new ArrayList<>();
    
    public static final int TIPO_ARASAAC_REST = 0;

    /**
     * Creates new form DibujarView
     */
    public DibujarView(C c, int idComposicion) {
        initComponents();
        myIconList.setModel(model);
        arasaacList.setModel(model2);
        controlador = c;
        this.idComposicion = idComposicion;
        botonBorrar.setVisible(false);
        //jButton1.setVisible(false); // botón "volver" queda en false hasta que se implemente otra funcionalidad
        //jScrollPane4.setVisible(false);
        //myIconPane.setTransferHandler(new MyIconPaneTH(myIconList));
        myIconList.setDropMode(DropMode.INSERT);
        myIconList.setTransferHandler(new DragImageList(myIconList, model, botonBorrar, controlador));
        arasaacList.setTransferHandler(new ArasaacTH(arasaacList, model2));
        myDrawPanel.setTransferHandler(new DrawPaneTH(myDrawPanel, botonBorrar, idComposicion));
        myDrawPanel.setName("miCanvas");
        botonBorrar.setTransferHandler(new BorrarTH());
        // Render para la lista REST de Arasaac
        ViewRender vr = new ViewRender();
        arasaacList.setCellRenderer(vr);
        jComboBox2.setRenderer(new ColorCellRenderer());
        jComboBox2.setBackground(Color.BLACK);
        pc = PintaControl.getInstance(botonBorrar, jToggleButton1, idComposicion);
        pc.setJpanel(myDrawPanel);
        botonBocata.setTransferHandler(new BocadilloTH());
        bocaText.setTransferHandler(new BocadilloTH());
        nubeButton.setTransferHandler(new BocadilloTH());
        controlador.rellenarComposicion(myDrawPanel, idComposicion);
    }

    private void limitaTexto() {
        while (bocaText.getLineCount() > 4) {
            String texto = bocaText.getText();
            int pos = bocaText.getCaretPosition();
            if (pos == 0) {
                pos = 1;
            }
            String salida = texto.substring(0, pos - 1).concat(texto.substring(pos, texto.length()));
            bocaText.setText(salida);
            bocaText.setCaretPosition(pos - 1);
        }
        boolean sePasa;
        do {
            sePasa = false;
            String[] lineas = bocaText.getText().split("\n");
            for (int i = 0; i < lineas.length; i++) {
                //if (lineas[i].length() > 10) {
                int tw = BocadilloTH.getTextWidth(lineas[i]);
                if (tw >= bocaText.getWidth() - 8 || tw > 99) {
                    sePasa = true;
                    String texto = bocaText.getText();
                    int pos = bocaText.getCaretPosition();
                    if (pos == 0) {
                        pos += 1;
                    }
                    String salida = texto.substring(0, pos - 1).concat(texto.substring(pos, texto.length()));
                    bocaText.setText(salida);
                    bocaText.setCaretPosition(pos - 1);
                }
            }
        } while (sePasa);
    }

    public JPanel getMyDrawPanel() {
        return myDrawPanel;
    }

    public JTextField getTituloTextField() {
        return compName;
    }
    
    public JList getArasaacList(){
        return arasaacList;
    }
    
    public JTextField getArasaacSeachText(){
        return arasaacSearch;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myIconPane = new javax.swing.JScrollPane();
        myIconList = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        myDrawPanel = new javax.swing.JPanel();
        botonBorrar = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        exportPdf = new javax.swing.JButton();
        saveImage = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        arasaacList = new javax.swing.JList<>();
        arasaacSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        bocaText = new javax.swing.JTextArea();
        botonBocata = new javax.swing.JButton();
        nubeButton = new javax.swing.JButton();
        compName = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(850, 450));
        setPreferredSize(new java.awt.Dimension(2000, 450));

        myIconList.setModel(new DefaultListModel());
        myIconList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        myIconList.setDragEnabled(true);
        myIconList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                myIconListMouseDragged(evt);
            }
        });
        myIconList.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                myIconListComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                myIconListComponentShown(evt);
            }
        });
        myIconList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                myIconListPropertyChange(evt);
            }
        });
        myIconPane.setViewportView(myIconList);

        jButton1.setText("<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setMinimumSize(new java.awt.Dimension(50, 23));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(50, 25));

        myDrawPanel.setForeground(new java.awt.Color(255, 255, 255));
        myDrawPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                myDrawPanelPropertyChange(evt);
            }
        });
        myDrawPanel.setLayout(null);
        jScrollPane1.setViewportView(myDrawPanel);

        botonBorrar.setBackground(new java.awt.Color(255, 6, 0));
        botonBorrar.setText("Borrar");

        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/print.jpeg"))); // NOI18N
        printButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printButton.setIconTextGap(0);
        printButton.setMinimumSize(new java.awt.Dimension(25, 25));
        printButton.setPreferredSize(new java.awt.Dimension(28, 28));
        printButton.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                printButtonComponentResized(evt);
            }
        });
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });
        printButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                printButtonPropertyChange(evt);
            }
        });

        exportPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/pdf.png"))); // NOI18N
        exportPdf.setPreferredSize(new java.awt.Dimension(28, 28));
        exportPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPdfActionPerformed(evt);
            }
        });

        saveImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/imagen.png"))); // NOI18N
        saveImage.setPreferredSize(new java.awt.Dimension(28, 28));
        saveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageActionPerformed(evt);
            }
        });

        arasaacList.setModel(myIconList.getModel());
        arasaacList.setDragEnabled(true);
        jScrollPane3.setViewportView(arasaacList);

        arasaacSearch.setToolTipText("Introduce Búsqueda");
        arasaacSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arasaacSearchActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Los símbolos pictográficos utilizados son propiedad del Gobierno de Aragón y han sido creados por Sergio Palao para ARASAAC (http://arasaac.org) que los distribuye bajo licencia Creative Commons (BY-NC-SA).");
        jScrollPane2.setViewportView(jTextArea1);

        jLabel1.setText("Buscar:");

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/flecha.png"))); // NOI18N
        jToggleButton1.setMaximumSize(new java.awt.Dimension(25, 25));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(1, 1));
        jToggleButton1.setPreferredSize(new java.awt.Dimension(10, 10));
        jToggleButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jToggleButton1StateChanged(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ColorCellRenderer.ROJO, ColorCellRenderer.VERDE, ColorCellRenderer.AMARILLO, ColorCellRenderer.NARANJA, ColorCellRenderer.AZUL, ColorCellRenderer.NEGRO }));
        jComboBox2.setSelectedItem("Negro");
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        bocaText.setColumns(9);
        bocaText.setRows(3);
        bocaText.setWrapStyleWord(true);
        bocaText.setAutoscrolls(false);
        bocaText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bocaText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                bocaTextCaretUpdate(evt);
            }
        });
        bocaText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bocaTextKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bocaTextKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bocaTextKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(bocaText);

        botonBocata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/bocadillo.jpeg"))); // NOI18N
        botonBocata.setFocusable(false);
        botonBocata.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBocata.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBocata.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                botonBocataMouseDragged(evt);
            }
        });
        botonBocata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBocataActionPerformed(evt);
            }
        });

        nubeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/nube.jpeg"))); // NOI18N
        nubeButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                nubeButtonMouseDragged(evt);
            }
        });

        compName.setText("Nuevo Dibujo");
        compName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                compNameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                compNameKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(compName, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exportPdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonBocata, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nubeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 1311, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(myIconPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(arasaacSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addComponent(myIconPane)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(arasaacSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addComponent(jButton1)
                            .addComponent(compName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(printButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(saveImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(exportPdf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(botonBorrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(botonBocata)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nubeButton)))
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controlador.eventoBoton(PictoController.Accion.Guarda, this.idComposicion);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void myIconListComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_myIconListComponentShown

    }//GEN-LAST:event_myIconListComponentShown

    private void myIconListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_myIconListPropertyChange

    }//GEN-LAST:event_myIconListPropertyChange

    private void myIconListComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_myIconListComponentResized
        if (drawn == false && myIconList.getWidth() > 51) {
            drawn = true;
            controlador.ponerRenderer(this.myIconList);
        }
    }//GEN-LAST:event_myIconListComponentResized

    private void myIconListMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myIconListMouseDragged

    }//GEN-LAST:event_myIconListMouseDragged

    private void printButtonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_printButtonPropertyChange

    }//GEN-LAST:event_printButtonPropertyChange

    private void printButtonComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_printButtonComponentResized

    }//GEN-LAST:event_printButtonComponentResized

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        AutoPrinter mPrinter = new AutoPrinter(myDrawPanel);
        // CompPrinter mPrinter = new CompPrinter(myDrawPanel);
        mPrinter.go();
    }//GEN-LAST:event_printButtonActionPerformed

    private void saveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes PNG", "png");
        fc.setFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            CompImager exporter = new CompImager(myDrawPanel, file);
            exporter.createFile();
        }
    }//GEN-LAST:event_saveImageActionPerformed

    private void exportPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportPdfActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Documentos PDF", "pdf");
        fc.setFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            CompPdfer exporter = new CompPdfer(myDrawPanel, file);
            exporter.convierte();
        }
    }//GEN-LAST:event_exportPdfActionPerformed

    private void arasaacSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arasaacSearchActionPerformed
        DefaultListModel mimodel = (DefaultListModel) arasaacList.getModel();
        mimodel.clear();

        giveMyFeedback(TIPO_ARASAAC_REST, null);
        arasaacSearch.setText("");
    }//GEN-LAST:event_arasaacSearchActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (jToggleButton1.isSelected()) {
            pc.entraModoPintar();
            jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/flecho.png")));
        } else {
            pc.dejaModoPintar();
            jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/teoytea/resources/aux/flecha.png")));
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jToggleButton1StateChanged

    }//GEN-LAST:event_jToggleButton1StateChanged

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        switch (jComboBox2.getSelectedIndex()) {
            case 0:
                Pintamelo.setColor(ColorEnum.Cenum.ROJO);
                break;
            case 1:
                Pintamelo.setColor(ColorEnum.Cenum.VERDE);
                break;
            case 2:
                Pintamelo.setColor(ColorEnum.Cenum.AMARILLO);
                break;
            case 3:
                Pintamelo.setColor(ColorEnum.Cenum.NARANJA);
                break;
            case 4:
                Pintamelo.setColor(ColorEnum.Cenum.AZUL);
                break;
            case 5:
                Pintamelo.setColor(ColorEnum.Cenum.NEGRO);
                break;
            default:
                Pintamelo.setColor(ColorEnum.Cenum.NEGRO);
                break;
        }
        jComboBox2.setBackground(Pintamelo.getFarbe());
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void bocaTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bocaTextKeyTyped
        limitaTexto();
    }//GEN-LAST:event_bocaTextKeyTyped

    private void bocaTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_bocaTextCaretUpdate

    }//GEN-LAST:event_bocaTextCaretUpdate

    private void botonBocataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBocataActionPerformed

    }//GEN-LAST:event_botonBocataActionPerformed

    private void botonBocataMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonBocataMouseDragged
        BocadilloTH.setTipo(DrawPaneTH.TipoLabel.Bocadillo);
        botonBocata.getTransferHandler().exportAsDrag(bocaText, evt, TransferHandler.MOVE);
    }//GEN-LAST:event_botonBocataMouseDragged

    private void bocaTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bocaTextKeyPressed

    }//GEN-LAST:event_bocaTextKeyPressed

    private void bocaTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bocaTextKeyReleased
        limitaTexto();
    }//GEN-LAST:event_bocaTextKeyReleased

    private void nubeButtonMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nubeButtonMouseDragged
        BocadilloTH.setTipo(DrawPaneTH.TipoLabel.Nube);
        nubeButton.getTransferHandler().exportAsDrag(bocaText, evt, TransferHandler.MOVE);
    }//GEN-LAST:event_nubeButtonMouseDragged

    private void myDrawPanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_myDrawPanelPropertyChange

    }//GEN-LAST:event_myDrawPanelPropertyChange

    private void compNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_compNameKeyTyped

    }//GEN-LAST:event_compNameKeyTyped

    private void compNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_compNameKeyReleased
        if (compName.getText().length() < 50) {
            giveComposicionFeedback(complisteners, myDrawPanel, compName.getText(), idComposicion);
        }
    }//GEN-LAST:event_compNameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> arasaacList;
    private javax.swing.JTextField arasaacSearch;
    private javax.swing.JTextArea bocaText;
    private javax.swing.JButton botonBocata;
    private javax.swing.JButton botonBorrar;
    private javax.swing.JTextField compName;
    private javax.swing.JButton exportPdf;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPanel myDrawPanel;
    private javax.swing.JList<String> myIconList;
    private javax.swing.JScrollPane myIconPane;
    private javax.swing.JButton nubeButton;
    private javax.swing.JButton printButton;
    private javax.swing.JButton saveImage;
    // End of variables declaration//GEN-END:variables

    @Override
    public <JButton> JButton getElementoBorrante() {
        return (JButton) botonBorrar;
    }

    @Override
    public <String> String getDraggableText() {
        return (String) bocaText.getText();
    }

    @Override
    public <JPanel> JPanel obtenCanvas() {
        return (JPanel) myDrawPanel;
    }

    @Override
    public void addComposicionFeedbackListener(ComposicionFeedbackListener toAdd) {
        complisteners.add(toAdd);
    }

    @Override
    public void giveComposicionFeedback(List<ComposicionFeedbackListener> complisteners, JPanel panel, String titulo, int id) {
        complisteners.forEach((listener) -> {
            listener.dandoFeedback(panel, titulo, id);
        });
    }

    @Override
    public void addMyFeedbackListener(MyFeedbackListener toAdd) {
        mylisteners.add(toAdd);
    }

    @Override
    public void giveMyFeedback(int tipo, Component feedback) {
        mylisteners.forEach((listener)->{
            listener.giveMyFeedback(tipo, feedback);
        });
    }
}
