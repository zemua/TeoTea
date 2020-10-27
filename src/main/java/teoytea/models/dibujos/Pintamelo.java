/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import static java.lang.Integer.min;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import teoytea.models.DrawPaneTH;

/**
 *
 * @author miguel
 */
public class Pintamelo {

    public static int FLECHA = 0;

    private static ColorEnum.Cenum COLOR = ColorEnum.Cenum.NEGRO;

    public Pintamelo() {
    }

    public static void setColor(ColorEnum.Cenum i) {
        COLOR = i;
    }

    public static ColorEnum.Cenum getColor() {
        return COLOR;
    }

    public static void meLoPintas(Point inicio, Point termina, int tipo, JLabel mlabel) {
        //switch (tipo) {
        //  case FLECHA:
        BufferedImage im = dataToBuferedImage(inicio, termina);
        mlabel.setIcon(new ImageIcon(im));
        mlabel.setBounds(min(inicio.x, termina.x), min(inicio.y, termina.y), im.getWidth(), im.getHeight());
        //  break;
        // }
    }
    
    public static BufferedImage dataToBuferedImage(Point inicio, Point termina, ColorEnum.Cenum color){
        Shape sh = Flecha.createArrowShape(inicio, termina);
        BufferedImage im = new BufferedImage(sh.getBounds().width, sh.getBounds().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = im.createGraphics();
        setGraphicColor(g2d, color.color());
        g2d.setClip(sh);
        g2d.translate(-g2d.getClipBounds().getMinX(), -g2d.getClipBounds().getMinY());
        g2d.setClip(sh);
        g2d.fill(sh);
        g2d.dispose();
        return im;
    }

    static BufferedImage dataToBuferedImage(Point inicio, Point termina) {
        return dataToBuferedImage(inicio, termina, COLOR);
    }

    public static JLabel dataToLabel(int id, int xini, int yini, int xfin, int yfin, int x, int y, ColorEnum.Cenum color) {
        JLabel jl = new JLabel();
        Point inicio = new Point(xini, yini);
        Point termina = new Point(xfin, yfin);
        BufferedImage bi = dataToBuferedImage(inicio, termina, color);
        jl.setIcon(new ImageIcon(bi));
        DrawPaneTH.setLabelDbId(jl, id);
        DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.FLECHA);
        jl.setBounds(x, y, bi.getWidth(), bi.getHeight());
        return jl;
    }
    
    private static void setGraphicColor(Graphics2D g2d, Color mcolor){
        g2d.setColor(mcolor);
    }

    private static void setGraphicColor(Graphics2D g2d) {
        setGraphicColor(g2d, getFarbe());
    }

    public static Color getFarbe() {
        return COLOR.color();
    }

    public static void labelToPanel(JLabel label, JPanel panel) {
        panel.add(label);
        panel.setComponentZOrder(label, 0);
        panel.revalidate();
        panel.repaint();
        System.out.println("añadida label al panel");
    }
}
