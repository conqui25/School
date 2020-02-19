/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg5filosofi;

/**
 *
 * @author Michele
 */
public class Main {

    final static int DIM = 5;
    static Thread[] threads = new Thread[DIM];
    static Tavolo t = new Tavolo(DIM);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < DIM; i++) {
            threads[i] = new Filosofo(t, DIM);
            threads[i].start();
            Thread.sleep(200);
        }
        while (t.getPiattiMangiati() < DIM) {
            Thread.sleep(500);
        }
        for (int i = 0; i < DIM; i++) {
            threads[i].interrupt();
        }
    }

}
