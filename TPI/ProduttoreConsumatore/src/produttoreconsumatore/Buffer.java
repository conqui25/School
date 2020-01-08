/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produttoreconsumatore;

import java.util.Random;

/**
 *
 * @author Michele
 */
public class Buffer {

    private int value;

    public Buffer() {
        setValue(new Random().nextInt(100));
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public synchronized int getValue() {
        return value;

    }
}
