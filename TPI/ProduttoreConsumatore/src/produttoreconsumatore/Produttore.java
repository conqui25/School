/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produttoreconsumatore;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class Produttore implements Runnable {

    private Buffer buffer;

    public Produttore(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            while (true) {
                Random random = new Random();
                int x = buffer.getValue();
                int y;
                do {
                    y = random.nextInt(100);
                } while (x == y);
                buffer.setValue(y);
                System.out.println("produttore = " + y);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ie) {
            System.out.println("finito (produttore)");
        }
    }
}
