/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.datatransfer.DataFlavor;
import static java.awt.datatransfer.DataFlavor.imageFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;
import teoytea.models.DrawPaneTH;

/**
 *
 * @author miguel
 */
public class BocadilloTH extends TransferHandler {

    private static final DataFlavor[] FLAVORS = {imageFlavor};
    public static final int M_FONT_SIZE = 12;
    public static final int M_SANGRIA = 10;

    /*public enum Tipo {
        bocadillo, globo;
    }*/
    private static DrawPaneTH.TipoLabel tipo = DrawPaneTH.TipoLabel.Bocadillo;

    public static void setTipo(DrawPaneTH.TipoLabel t) {
        tipo = t;
    }

    public static int getTextWidth(String txt, int fontSize) {
        Graphics2D g = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR).createGraphics();
        setFont(g, fontSize);
        int width = g.getFontMetrics().stringWidth(txt);
        g.dispose();
        return width;
    }

    public static int getTextWidth(String txt) {
        return getTextWidth(txt, M_FONT_SIZE);
    }

    public static int getTextArrayMaxWidth(String[] txt) {
        int width = 0;
        for (String text : txt) {
            int w = getTextWidth(text);
            if (w > width) {
                width = w;
            }
        }
        return width;
    }

    public static int getTextHeight(int fontSize) {
        Graphics2D g = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR).createGraphics();
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int height = g.getFontMetrics().getHeight();
        g.dispose();
        return height;
    }

    public static int getTextHeight() {
        return getTextHeight(M_FONT_SIZE);
    }

    private static void setFont(Graphics2D g, int fontSize) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    }

    public static Font getFont() {
        return new Font("TimesRoman", Font.PLAIN, M_FONT_SIZE);
    }

    public static ImageIcon dameBocata(int width, int height, int fontSize, int sangria, int alturaChar, String[] txts, Color mcolor, double aumento) {
        int altura = (int) ((txts.length * alturaChar + alturaChar / 2) * aumento);
        int ancho = widthConSangriaPaTexto(txts, (int) (fontSize*aumento), aumento);
        int altoPico = (int) (16 * aumento);
        BufferedImage bi = new BufferedImage((int) (ancho), (int) (altura + altoPico), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        setFont(g, (int) (fontSize * aumento));
        g.setColor(mcolor);
        Polygon p;
        g.fill(new RoundRectangle2D.Double(0, 0, ancho - 1, altura, 10, 10));
        p = new Polygon();
        p.addPoint((int) (ancho * 3 / 5), (int) (altura - 1));
        p.addPoint((int) (ancho * 1 / 5), (int) (altura + altoPico));
        p.addPoint((int) (ancho * 2 / 5), (int) (altura - 1));
        g.fillPolygon(p);
        if (g.getColor() == Color.BLACK) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }
        for (int i = 0; i < txts.length; i++) {
            g.drawString(txts[i], (int) (sangria * aumento / 2), (int) (alturaChar * aumento * (1 + i)));
        }
        g.dispose();
        ImageIcon ii = new ImageIcon(bi);
        return ii;
    }

    private ImageIcon dameBocata(int width, int height, int fontSize, int sangria, int alturaChar, String[] txts, Color mcolor) {
        return dameBocata(width, height, fontSize, sangria, alturaChar, txts, mcolor, 1);
    }

    private ImageIcon dameBocata(int width, int height, int fontSize, int sangria, int alturaChar, String[] txts) {
        return dameBocata(width, height, fontSize, sangria, alturaChar, txts, Pintamelo.getFarbe());
    }

    public static ImageIcon dameNube(int width, int height, int fontSize, int sangria, int alturaChar, String[] txts, Color mcolor, double aumento) {
        int altura = (int) ((txts.length * alturaChar + alturaChar / 2) * aumento);
        int ancho = widthConSangriaPaTexto(txts, (int) (fontSize*aumento), aumento);
        int altoPico = (int) (24 * aumento);
        int espesorBurbuja = (int) (10 * aumento);
        int totwidth = (int) (ancho + espesorBurbuja * 2);
        int totheight = (int) (altura + altoPico + espesorBurbuja * 2);
        BufferedImage bi = new BufferedImage(totwidth, totheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        setFont(g, (int) (fontSize * aumento));
        g.setColor(mcolor);
        Polygon p = new Polygon();
        g.fill(new RoundRectangle2D.Double(espesorBurbuja, espesorBurbuja, ancho - (espesorBurbuja * 2), altura - (espesorBurbuja * 2), 10, 10));
        /*
        dibujar las burbujas alrededor
         */
        totheight -= altoPico;
        int horizontales = (int) (totwidth / (25 * aumento));
        if (horizontales < 2) {
            horizontales = 2;
        }
        //System.out.println("horizontales " + horizontales);
        int verticales = (int) (totheight / (25 * aumento));
        if (verticales < 2) {
            verticales = 2;
        }
        //System.out.println("verticales " + verticales);
        int bwidth = (6 * totwidth / horizontales) / 5;
        int bheight = (6 * totheight / verticales) / 5;

        int desfaseW = ((bwidth * horizontales) - totwidth) / horizontales;
        int desfaseH = ((bheight * verticales) - totheight) / verticales;

        for (int i = 0; i < horizontales; i++) {
            for (int j = 0; j < verticales; j++) {
                if (i == 0 || i == horizontales - 1 || j == 0 || j == verticales - 1) {
                    int w = i * totwidth / horizontales - i * desfaseW;
                    int h = j * totheight / verticales - j * desfaseH;
                    // experimentando para hacer la nube más redonda...
                    // pero ocupa un espacio exagerado
                    /*int alto = bheight + (int) (bheight * Math.sin(Math.toRadians(180 * i / (horizontales - 1)))) / 3;
                    if (j == 0) {
                        h = h - (int)(bheight * Math.sin(Math.toRadians(180 * i / (horizontales - 1)))) / 3;
                    }*/
                    g.fill(new Ellipse2D.Double(w, h, bwidth, bheight));
                    g.draw(new Ellipse2D.Double(w, h, bwidth, bheight));
                }
            }
        }

        /*
        dibujar las burbujas abajo en lugar del pico
         */
        int delimiter = altoPico;
        System.out.println(altoPico);
        /*
        if (delimiter > totwidth/3){
            delimiter = totwidth/3;
        }
         */
        // Orden de parámetros: X, Y, Width, Height -> para las 3 pelotas debajo de la nube
        g.fillOval(totwidth * 2 / 5, totheight - espesorBurbuja / 3 - totheight / 10, delimiter * 2 / 3, delimiter * 2 / 3);
        g.fillOval((int) (totwidth * 2 / 5 - (8 * aumento)), (int) (totheight - espesorBurbuja / 3 - totheight / 10 + (10 * aumento)), delimiter * 4 / 9, delimiter * 4 / 9);
        g.fillOval((int) (totwidth * 2 / 5 - (15 * aumento)), (int) (totheight - espesorBurbuja / 3 - totheight / 10 + (15 * aumento)), delimiter / 4, delimiter / 4);
        //g.fillOval(totwidth * 5 / 16, totheight + delimiter * 3 / 7 - espesorBurbuja / 2, delimiter * 4 / 9, delimiter * 4 / 9);
        //g.fillOval(totwidth / 5, totheight + delimiter * 2 / 3 - espesorBurbuja / 2, delimiter / 4, delimiter / 4);

        /*
        y finalmente poner el texto encima
         */
        if (g.getColor() == Color.BLACK) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }
        for (int i = 0; i < txts.length; i++) {
            g.drawString(txts[i], (int) (espesorBurbuja + sangria * aumento / 3), (int) (espesorBurbuja + alturaChar * aumento * (1 + i)));
        }
        g.dispose();
        ImageIcon ii = new ImageIcon(bi);
        return ii;
    }

    private ImageIcon dameNube(int width, int height, int fontSize, int sangria, int alturaChar, String[] txts, Color mcolor) {
        return dameNube(width, height, fontSize, sangria, alturaChar, txts, mcolor, 1);
    }

    private ImageIcon dameNube(int width, int height, int fontSize, int sangria, int alturaChar, String[] txts) {
        return dameNube(width, height, fontSize, sangria, alturaChar, txts, Pintamelo.getFarbe());
    }

    public JLabel dataToLabel(int dbid, DrawPaneTH.TipoLabel mtipo, String[] mtexto, int dbx, int dby, ColorEnum.Cenum mcolor) {
        JLabel jl = new JLabel();
        DrawPaneTH.setLabelDbId(jl, dbid);

        int width = widthConSangria(getTextArrayMaxWidth(mtexto), 1.0);
        int height = getTextArrayHeight(mtexto);
        int fontSize = M_FONT_SIZE;
        int sangria = M_SANGRIA;
        int alturaChar = getTextHeight();

        ImageIcon icono;
        switch (mtipo) {
            case Nube:
                icono = dameNube(width, height, fontSize, sangria, alturaChar, mtexto, mcolor.color());
                DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.NUBE);
                break;
            default:
                icono = dameBocata(width, height, fontSize, sangria, alturaChar, mtexto, mcolor.color());
                DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.BOCADILLO);
                break;
        }
        jl.setIcon(icono);
        jl.setBounds(dbx, dby, icono.getIconWidth(), icono.getIconHeight());

        return jl;
    }

    int getTextArrayHeight(String[] txts) {
        int alturaChar = getTextHeight();
        int height = txts.length * alturaChar + alturaChar / 2;
        return height;
    }

    static private int widthConSangriaPaTexto(String[] txts, int fontSize, double aumento) {
        int width;
        for (int i = width = 0; i < txts.length; i++) {
            int gross = getTextWidth(txts[i], fontSize);
            if (gross > width) {
                width = gross;
            }
        }

        width = widthConSangria(width, aumento); // deja sangría izquierda y derecha
        return width;
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        return new Transferable() {
            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                System.out.println("data flavor supported: " + FLAVORS[0].equals(flavor));
                return FLAVORS[0].equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor mFlavor) {
                if (isDataFlavorSupported(mFlavor) && comp instanceof JTextArea && !((JTextArea) comp).getText().equals("")) {
                    String txt = ((JTextArea) comp).getText();
                    String[] txts = txt.split("\n");
                    int fontSize = M_FONT_SIZE;
                    int sangria = M_SANGRIA;

                    /*
                    Obtener las dimensiones de las cosas.
                     */
                    int alturaChar = getTextHeight();
                    int height = getTextArrayHeight(txts);
                    int width = widthConSangriaPaTexto(txts, fontSize, 1.0);

                    JLabel jl = new JLabel();

                    if (tipo == DrawPaneTH.TipoLabel.Bocadillo) {
                        jl.setIcon(dameBocata(width, height, fontSize, sangria, alturaChar, txts));
                        DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.BOCADILLO);
                    } else {
                        jl.setIcon(dameNube(width, height, fontSize, sangria, alturaChar, txts));
                        DrawPaneTH.setLabelArasaacId(jl, DrawPaneTH.NUBE);
                    }

                    return jl;
                }
                return null;
            }

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return FLAVORS;
            }
        };
    }

    static private int widthConSangria(int w, double aumento) {
        int width = (int) (w + aumento * M_SANGRIA * 13 / 10);
        return width;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (source instanceof JTextArea && action == MOVE) {
            JTextArea jta = (JTextArea) source;
            jta.setText("");
        }
    }

    @Override
    public int getSourceActions(JComponent c) {
        if (c instanceof JTextArea && !((JTextArea) c).getText().equals("")) {
            System.out.println("get source actions: mover");
            return TransferHandler.MOVE;
        }
        System.out.println("get source actions: none");
        return TransferHandler.NONE;
    }

}
