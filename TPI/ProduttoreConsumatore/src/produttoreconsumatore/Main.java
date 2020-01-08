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
public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();
        Thread prod = new Thread(new Produttore(buffer));
        Thread cons = new Thread(new Consumatore(buffer));
        prod.start();
        cons.start();
        Thread.sleep(10000);
        prod.interrupt();
        cons.interrupt();
    }
}
