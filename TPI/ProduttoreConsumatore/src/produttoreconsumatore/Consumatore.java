/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produttoreconsumatore;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class Consumatore implements Runnable {

    private Buffer buffer;
    private int[] readValue = new int[5];
    private int position = 0;

    public Consumatore(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            while (true) {

                int val = buffer.getValue();
                if (position == 0 || val != readValue[position - 1]) {
                    System.out.println("readValue[" + position + "] = " + val);
                    readValue[position] = val;
                    position++;
                    if (position == 5) {
                        System.out.println("\n" + "media:" + average() + "\n");
                        position = 0;
                    }
                }else
                    System.out.println("buffer = " + val);
                Thread.sleep(100);

            }
        } catch (InterruptedException ie) {
            System.out.println("finito (consumatore)");
        }
    }

    public int average() {
        int average = 0;
        for (int i = 0; i < 5; i++) {
            average += readValue[i];
        }
        return average / 5;
    }
}
