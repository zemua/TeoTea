/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import teoytea.models.arasaac.ArchivosArasaac;
import teoytea.models.db.DBFlechas;
import teoytea.models.db.DBbocadillos;
import teoytea.models.dibujos.BocadilloTH;
import teoytea.models.dibujos.ColorEnum;
import teoytea.models.dibujos.Pintamelo;

/**
 *
 * @author miguel
 */
public class CompImager {

    Component mCom;
    File mFile;

    public CompImager(Component comp, File file) {
        mCom = comp;
        mFile = file;
        String ruta = mFile.getPath();
        if (!ruta.endsWith(".png")) {
            ruta = ruta.concat(".png");
            mFile = new File(ruta);
        }
    }

    private double minimoAumento(JPanel panel) {
        Component[] components = panel.getComponents();
        double f = -1.0;
        for (Component component : components) {
            if (component instanceof JLabel && DrawPaneTH.checkTipoLabel((JLabel) component) == DrawPaneTH.TipoLabel.Arasaac) {
                String id = DrawPaneTH.getLabelArasaacId((JLabel) component);
                BufferedImage image = ArchivosArasaac.getInstance().getLocalImage(id);
                int localWidth = image.getWidth();
                int localHeight = image.getHeight();
                int labelWidth = ((JLabel) component).getIcon().getIconWidth();
                int labelHeight = ((JLabel) component).getIcon().getIconHeight();
                if (!(labelWidth > localWidth) && !(labelHeight > localHeight)) {
                    double aumento = Math.min(((double) localWidth / (double) labelWidth), ((double) localHeight / (double) labelHeight));
                    if (aumento > 1.0 && (aumento < f || f < 1.0)) {
                        f = aumento;
                    }
                }
            }
        }
        if (f < 1.0) {
            return 10;
        }
        return f;
    }

    private ImageIcon resizedPicto(JLabel original, double aumento) {
        String sid = DrawPaneTH.getLabelArasaacId(original);
        // obtenemos la imagen a tamaño original
        BufferedImage im = ArchivosArasaac.getInstance().getLocalImage(Integer.parseInt(sid));
        // alculamos máxima "ampliación" del label para reducir la original, este dato puede venir de "minimoAumento()"
        int targetWidth = (int) (original.getIcon().getIconWidth() * aumento);
        int targetHeight = (int) (original.getIcon().getIconHeight() * aumento);
        // reducimos la original y la metemos al nuevo label
        ImageIcon res = ArchivosModel.scaleImage(new ImageIcon(im), targetWidth, targetHeight);
        return res;
    }

    private ImageIcon resizedArrow(JLabel original, double aumento) {
        ImageIcon ic;
        try {
            ResultSet rs = new DBFlechas().getUnaFlecha(DrawPaneTH.getLabelDbId(original));
            rs.next();
            int xi = (int) (rs.getInt("X_INICIO") * aumento);
            int yi = (int) (rs.getInt("Y_INICIO") * aumento);
            int xf = (int) (rs.getInt("X_FINAL") * aumento);
            int yf = (int) (rs.getInt("Y_FINAL") * aumento);
            Point p1 = new Point(xi, yi);
            Point p2 = new Point(xf, yf);
            BufferedImage bi = Pintamelo.dataToBuferedImage(p1, p2, arrowColor(original));
            ic = new ImageIcon(bi);
        } catch (SQLException ex) {
            Logger.getLogger(CompImager.class.getName()).log(Level.SEVERE, null, ex);
            ic = null;
        }
        return ic;
    }

    private ColorEnum.Cenum arrowColor(JLabel original) {
        ColorEnum.Cenum color;
        try {
            ResultSet rs = new DBFlechas().getUnaFlecha(DrawPaneTH.getLabelDbId(original));
            rs.next();
            int mcolor = rs.getInt("COLOR");
            color = ColorEnum.getCenumFromNum(mcolor);
        } catch (SQLException ex) {
            Logger.getLogger(CompImager.class.getName()).log(Level.SEVERE, null, ex);
            color = ColorEnum.Cenum.NEGRO;
        }
        return color;
    }

    private ImageIcon resizedBocata(JLabel original, double aumento) {
        ImageIcon icon;
        try {
            int width;
            int height;
            int fontSize;
            int sangria;
            int alturaChar;
            String[] txts;
            Color mcolor;
            double maumento;

            ResultSet rs = new DBbocadillos().getBocadillo(DrawPaneTH.getLabelDbId(original));
            rs.next();
            width = original.getIcon().getIconWidth();
            height = original.getIcon().getIconHeight();
            fontSize = BocadilloTH.M_FONT_SIZE;
            sangria = BocadilloTH.M_SANGRIA;
            alturaChar = BocadilloTH.getTextHeight();
            String txt = rs.getString("TEXTO");
            txts = txt.split("\n");
            mcolor = ColorEnum.getCenumFromNum(rs.getInt("COLOR")).color();
            maumento = aumento;

            if (DrawPaneTH.checkTipoLabel(original) == DrawPaneTH.TipoLabel.Nube) {
                icon = BocadilloTH.dameNube(width, height, fontSize, sangria, alturaChar, txts, mcolor, maumento);
            } else {
                icon = BocadilloTH.dameBocata(width, height, fontSize, sangria, alturaChar, txts, mcolor, aumento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompImager.class.getName()).log(Level.SEVERE, null, ex);
            icon = null;
        }
        return icon;
    }

    private BufferedImage redimensionarPanel(JPanel panel, double aumento) {
        int w = panel.getWidth();
        int h = panel.getHeight();

        BufferedImage bi = new BufferedImage((int) (w * aumento), (int) (h * aumento), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        Component[] components = panel.getComponents();
        Component compOrden[] = new Component[panel.getComponentCount()];
        for (int k=0; k<components.length;k++){
            compOrden[panel.getComponentCount()-1-panel.getComponentZOrder(components[k])] = components[k];
        }
        for (Component component : compOrden){
            if (component instanceof JLabel) {
                DrawPaneTH.TipoLabel tipo = DrawPaneTH.checkTipoLabel((JLabel) component);
                ImageIcon ic;
                switch (tipo) {
                    case Arasaac:
                        ic = resizedPicto((JLabel) component, aumento);
                        break;
                    case Flecha:
                        ic = resizedArrow((JLabel) component, aumento);
                        break;
                    default:
                        ic = resizedBocata((JLabel) component, aumento);
                        break;
                }
                int lx = ((JLabel) component).getX();
                int ly = ((JLabel) component).getY();
                AffineTransform at = AffineTransform.getTranslateInstance(lx * aumento, ly * aumento);
                g.drawImage(ic.getImage(), at, (Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) -> true);
            }
        }
        g.dispose();
        return bi;
    }

    public ImageIcon createFilePeque() {
        Dimension size = mCom.getSize();
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        mCom.paint(g2);
        g2.dispose();
        //System.out.println("creando png en archivo... " + mFile.getPath());
        ImageIcon imi = null;
        try {
            ImageIO.write(image, "png", mFile);
            BufferedImage bi = ImageIO.read(mFile);
            imi = new ImageIcon(bi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imi;
    }

    public ImageIcon createFile() {
        if (!(mCom instanceof JPanel)) {
            return null;
        }
        BufferedImage image = redimensionarPanel((JPanel) mCom, minimoAumento((JPanel) mCom));
        ImageIcon imi;
        try {
            ImageIO.write(image, "png", mFile);
            imi = new ImageIcon(image);
        } catch (IOException ex) {
            Logger.getLogger(CompImager.class.getName()).log(Level.SEVERE, null, ex);
            imi = null;
        }
        return imi;
    }
}
