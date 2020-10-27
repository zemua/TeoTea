/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import java.util.ArrayList;
import javax.swing.JLabel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import teoytea.interfaces.MyFeedbackListener;

/**
 *
 * @author miguel
 */
public class SimpleAraRestTest {

    public SimpleAraRestTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSimpleAraRest() throws InterruptedException {
        System.out.println("SimpleAraRest");
        SimpleAraRest instance = new SimpleAraRest();
        String target = "castillo";
        ArrayList<JLabel> labellist = new ArrayList<>();

        instance.addMyFeedbackListener((MyFeedbackListener<JLabel>) new MyFeedbackListener<JLabel>() {
            @Override
            public void giveMyFeedback(int tipo, JLabel label) {
                switch (tipo) {
                    case SimpleAraRest.TIPO_ADD_LABEL:
                        labellist.add(label);
                        System.out.println("hilo: " + Thread.currentThread());
                        break;
                    case SimpleAraRest.TIPO_LIMPIA_PLACEHOLDER:
                        labellist.remove(0);
                        break;
                    case SimpleAraRest.TIPO_TIME_OUT:
                        //labellist.remove(0);
                        break;
                }
            }
        });

        instance.vamos(target);
        /*
        System.out.println("esperando a descargar las imágenes");
        while(labellist.size()<6){
        }
        System.out.println("cargadas todas");
        assertEquals(6, labellist.size());
        */
        assertEquals(1, instance.getId());

        /**
         * probando que las funciones "interrumpir" de las clases
         * ArasaacInvocationCallback y de SimpleImageFetcher funcionan Primero
         * creamos una consulta inmediatamente creamos otra consulta y a los 5
         * segundos comprobamos que la primera no devuelve nada
         */
        ArrayList<JLabel> labellist2 = new ArrayList<>();
        ArrayList<JLabel> labellist3 = new ArrayList<>();

        SimpleAraRest instance2 = new SimpleAraRest();
        assertEquals(2, instance2.getId());
        instance2.addMyFeedbackListener((MyFeedbackListener<JLabel>) (tipo, label) -> {
            if (tipo == SimpleAraRest.TIPO_ADD_LABEL) {
                labellist2.add(label);
            }
        });
        assertEquals(0, labellist2.size());
        labellist2.add(new JLabel(SimpleAraRest.PLACEHOLDER));
        assertEquals(1, labellist2.size());
        instance2.limpiaPlaceholder();
        assertEquals(1, labellist2.size());
        instance2.vamos("asdaeqwevqwervwervwer");

        SimpleAraRest instance3 = new SimpleAraRest(); // interrumpe al 2
        assertEquals(3, instance3.getId());

        instance3.addMyFeedbackListener((MyFeedbackListener<JLabel>) (tipo, label) -> {
            switch (tipo) {
                case SimpleAraRest.TIPO_ADD_LABEL:
                    labellist3.add(label);
                    System.out.println("hilo: " + Thread.currentThread());
                    break;
                case SimpleAraRest.TIPO_TIME_OUT:
                    //labellist3.remove(0);
                    break;
                case SimpleAraRest.TIPO_LIMPIA_PLACEHOLDER:
                    labellist3.remove(0);
                    break;
                case SimpleAraRest.TIPO_CLEAR_LIST:
                    labellist3.clear();
                    break;
                case SimpleAraRest.TIPO_ADD_ERROR:
                    labellist3.add(label);
                    break;
            }
        });

        instance3.vamos("castillo");

        assertTrue(!instance.esLaMasReciente());
        assertTrue(!instance2.esLaMasReciente());
        assertTrue(instance3.esLaMasReciente());

        assertEquals(0, instance3.getPictoList().size()); //no le dio tiepo a cargarlos
        instance3.addPicto(2327); // addPicto
        assertEquals(1, instance3.getPictoList().size());
        assertEquals(1, labellist3.size()); // tiene el label de error o placeholder

        /*
        System.out.println("esperando a descargar las imágenes");
        while(labellist3.size()<6){}
        System.out.println("cargadas todas");
        assertEquals(6, labellist3.size());
        */

        /**
         * Hacemos todos los tests aquí dentro para evitar tener que enviar
         * consultas adicionales a Arasaac
         */
        /**
         * Add picto list lo testamos arriba porque cuando termina la consulta,
         * el pictolist se cierra
         */
        //assertEquals(6, instance3.getPictoList().size());
        //instance3.addPicto(1);
        //assertEquals(7, instance3.getPictoList().size());
        /**
         * cierra picto
         */
        instance3.cierraPicto();
        instance3.addPicto(2);
        assertEquals(1, instance3.getPictoList().size());
        instance3.addPicto(2);
        assertEquals(1, instance3.getPictoList().size());
        /**
         * Añade error label
         */
        instance3.giveMyFeedback(SimpleAraRest.TIPO_CLEAR_LIST, null);
        instance3.anhadeErrorLabel();
        assertEquals(1, labellist3.size());
        assertTrue(((JLabel) labellist3.get(0)).getText().equals(SimpleAraRest.ERRORPH));
    }

}
