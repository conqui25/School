/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veicoli;

/**
 *
 * @author Michele
 */
public class TargaEsistente extends Exception{
    public String targa;

    public TargaEsistente(String targa) {
        this.targa = targa;
    }

    @Override
    public String toString() {
        return "esiste gia un veicolo con la targa:" + targa;
    }
    
}
