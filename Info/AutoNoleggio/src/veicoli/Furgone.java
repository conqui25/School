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
public class Furgone extends Veicolo{
    private int capCarico;

    public Furgone(String targa, int numMatricola, String marca, String modello, int cilindrata, int anno, int capSerbatoio, int capCarico) {
        super(targa, numMatricola, marca, modello, cilindrata, anno, capSerbatoio);
        this.capCarico = capCarico;
       
    }

    public Furgone(Furgone furgone) {
        super(furgone.getTarga(), furgone.getNumMatricola(), furgone.getMarca(), furgone.getModello(), furgone.getCilindrata(), furgone.getAnno(), furgone.getCapSerbatoio());
        this.capCarico = furgone.getCapCarico();
    }

    public int getCapCarico() {
        return capCarico;
    }
    
    
    @Override
    public double calcCosto(long day, int km, int carb) {
        return (70*day) + (km/30) + (2*carb);
    }

    @Override
    public String toString() {
        return "Furgone" + "\n" +
                "Targa: " + getTarga() + "\n" +
                "Numero matricola: " + getNumMatricola() + "\n" +
                "Marca: " + getMarca() + "\n" +
                "Modello: " + getModello() + "\n" +
                "Cilindrata: " + getCilindrata() + "\n" +
                "Anno imm.: " + getAnno() + "\n" +
                "Cap serbatoio: " + getCapSerbatoio() + "\n" + 
                "Cap carico: " + capCarico + "\n";
    }

    @Override
    public String stringForFile() {
        return "Furgone" + ", " + 
                getTarga() + ", " +
                getNumMatricola() + ", " +
                getMarca() + ", " +
                getModello() + ", " +
                getCilindrata() + ", " +
                getAnno() + ", " +
                getCapSerbatoio() + ", " + 
                capCarico;
    }

    @Override
    public String type() {
        return "Furgone";
    }

}
