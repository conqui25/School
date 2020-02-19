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
public class Auto extends Veicolo{
    int numPasseggeri;

    public Auto(String targa, int numMatricola, String marca, String modello, int cilindrata, int anno, int capSerbatoio, int numPasseggeri) {
        super(targa, numMatricola, marca, modello, cilindrata, anno, capSerbatoio);
        this.numPasseggeri = numPasseggeri;
    }
    
    @Override
    public double calcCosto(long day, int km, int carb) {
        return (50*day) + (km/25) + (2*carb);
    }

    @Override
    public String toString() {
        return "Auto" + "\n" + 
                "Targa: " + getTarga() + "\n" +
                "Numero matricola: " + getNumMatricola() + "\n" +
                "Marca: " + getMarca() + "\n" +
                "Modello: " + getModello() + "\n" +
                "Cilindrata: " + getCilindrata() + "\n" +
                "Anno imm.: " + getAnno() + "\n" +
                "Cap serbatoio: " + getCapSerbatoio() + "\n" + 
                "Num passeggeri: " + numPasseggeri + "\n";
    }

    @Override
    public String stringForFile() {
        return "Auto" + ", " + 
                getTarga() + ", " +
                getNumMatricola() + ", " +
                getMarca() + ", " +
                getModello() + ", " +
                getCilindrata() + ", " +
                getAnno() + ", " +
                getCapSerbatoio() + ", " + 
                numPasseggeri;
    }

    @Override
    public String type() {
        return "Auto";
    }/*
    int choose = Integer.parseInt(JoptionPane.SHowInputDialog(null, "0: nuova auto\n1: "));
    switch(){
    
    
    }
    */
}
