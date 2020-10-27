/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.dibujos;

import java.awt.Color;

/**
 *
 * @author miguel
 */
public class ColorEnum {

    public enum Cenum {
        ROJO(Color.RED, 50), VERDE(Color.GREEN, 51), AMARILLO(Color.YELLOW, 52), NARANJA(Color.ORANGE, 53), AZUL(Color.CYAN, 54), NEGRO(Color.BLACK, 55);

        Color color;
        int i;

        Cenum(Color c, int o) {
            color = c;
            i = o;
        }

        Cenum() {
            color = Color.BLACK;
            i = 55;
        }

        public Color color() {
            return this.color;
        }

        public int num() {
            return this.i;
        }
    }

    public static Cenum getCenumFromNum(int i) {
        switch (i) {
            case 50:
                return Cenum.ROJO;
            case 51:
                return Cenum.VERDE;
            case 52:
                return Cenum.AMARILLO;
            case 53:
                return Cenum.NARANJA;
            case 54:
                return Cenum.AZUL;
            default:
                return Cenum.NEGRO;
        }
    }
}
