package veicoli;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michele
 */
public abstract class Veicolo {
    private String targa;
    private int numMatricola;
    private String marca;
    private String modello;
    private int cilindrata;
    private int anno;
    private int capSerbatoio;

    public Veicolo(String targa, int numMatricola, String marca, String modello, int cilindrata, int anno, int capSerbatoio) {
        this.targa = targa;
        this.numMatricola = numMatricola;
        this.marca = marca;
        this.modello = modello;
        this.cilindrata = cilindrata;
        this.anno = anno;
        this.capSerbatoio = capSerbatoio;
    }
    
    public String getTarga() {
        return targa;
    }

    public int getNumMatricola() {
        return numMatricola;
    }

    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    public int getCilindrata() {
        return cilindrata;
    }
    
    public int getAnno() {
        return anno;
    }

    public int getCapSerbatoio() {
        return capSerbatoio;
    }

    
    
    
    public abstract double calcCosto(long day, int km, int carb);
    
    @Override
    public abstract String toString();
    public abstract String stringForFile();
    public abstract String type();
}
