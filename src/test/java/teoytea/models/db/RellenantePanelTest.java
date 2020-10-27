/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.db;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import teoytea.controllers.PictoController;
import teoytea.interfaces.Borrante;
import teoytea.interfaces.ControladorInterface;
import teoytea.models.dibujos.ColorEnum;
import teoytea.views.DibujarView;

/**
 *
 * @author miguel
 */
public class RellenantePanelTest {

    public RellenantePanelTest() {
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

    /**
     * Test of rellenarPanel method, of class RellenantePanel.
     */
    @Test
    public void testRellenarPanel_JPanel_int() {
        // ya se testea en el de abajo
    }

    /**
     * Test of rellenarPanel method, of class RellenantePanel.
     */
    @Test
    public void testRellenarPanel() {
        int idcomp = 0;
        class Padre extends JPanel implements Borrante {
            @Override
            public JButton getElementoBorrante(){
                return new JButton();
            }
        }
        JPanel panel = new JPanel();
        Padre padre = new Padre();
        padre.add(panel);

        DBbocadillos b = new DBbocadillos();
        b.deleteAllBocadillos(idcomp);
        long bocata = b.anhadirBocadilloToComp(idcomp, "a", "b", 20, 20, ColorEnum.Cenum.ROJO);

        DBPictogramas p = new DBPictogramas();
        p.deleteAllPictos(idcomp);
        long picto = p.anhadirPictoToComp(idcomp, 5422, 100, 100, 100, 100);

        DBFlechas f = new DBFlechas();
        f.deleteAllFlechas(idcomp);
        long flecha = f.anhadirFlechaToComp(idcomp, 10, 10, 20, 20, 30, 30, ColorEnum.Cenum.AMARILLO);

        RellenantePanel rp = new RellenantePanel(panel, idcomp);
        rp.rellenarPanel();
        assertEquals(3, panel.getComponentCount());

        b.borrarBocadilloDeComp((int) bocata);
        p.borrarPictoDeComp((int) picto);
        f.borrarFlechaDeComp((int) flecha);
    }

}
