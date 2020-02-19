/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5filosofi;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class Filosofo extends Thread {

    private Tavolo tav;
    private static int idCont = 0;
    private int id;
    private boolean f1 = false;
    private boolean f2 = false;
    private int dim;

    public Filosofo(Tavolo tav, int dim) {
        this.tav = tav;
        id = idCont++;
        this.dim = dim;
    }

    @Override
    public void run() {
        try {
            if (id < dim - 1) {
                tav.get(id, id);
                tav.get(id, id + 1);
                Thread.sleep(500);
                tav.free(id, id, id + 1);
            } else {
                tav.get(id, 0);
                tav.get(id, id);
                Thread.sleep(500);
                tav.free(id, 0, id);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Filosofo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
