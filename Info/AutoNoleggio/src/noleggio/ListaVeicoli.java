/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noleggio;

import java.util.HashMap;
import veicoli.Veicolo;
import veicoli.TargaEsistente;

/**
 *
 * @author Michele
 */
public class ListaVeicoli {
    private HashMap<String, Veicolo> vetture;
    
    public ListaVeicoli() {
        this.vetture = new HashMap<>();
    }

    public Veicolo getVeicolo(String targa) {
        return vetture.get(targa);
    }

    public boolean addVeicolo(Veicolo v) throws TargaEsistente {
        if (vetture.putIfAbsent(v.getTarga(), v) != null){
            //putIfAbsent restituisce null se riesce ad associare il valore, invece ritorna il value(auto) se non riesce
            throw new TargaEsistente(v.getTarga());
        }else{
            return true;
        }
    }
    

    public HashMap<String, Veicolo> getVetture() {
        return vetture;
    }
}
