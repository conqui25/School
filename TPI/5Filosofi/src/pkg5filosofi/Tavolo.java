/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5filosofi;

import java.util.Arrays;

/**
 *
 * @author Michele
 */
public class Tavolo {

    private boolean[] forchette;
    private int dim;
    private int piattiMangiati = 0;

    public Tavolo(int dim) {
        this.dim = dim;
        this.forchette = new boolean[dim];
        for(int i = 0; i < dim; i++){
            forchette[i] = true;
        }
    }

    public synchronized void get(int id, int i) throws InterruptedException {
        while (!forchette[i]) {
            System.out.println(id + " - " + "aspetto forchetta - " + i);
            wait();
        }
        System.out.println(id + " - " + "prendo forchetta - " + i);
        forchette[i] = (false);
        System.out.println(Arrays.toString(forchette));
        notifyAll();
    }

    public synchronized void free(int id, int i, int j) {
        System.out.println(id + " - " + "libero forchetta - " + i + " - " + j);
        forchette[i] = (true);
        forchette[j] = (true);
        System.out.println(Arrays.toString(forchette));
        piattiMangiati++;
        notifyAll();
    }

    public synchronized int getPiattiMangiati() {
        return piattiMangiati;
    }
}
