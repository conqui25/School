/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noleggio;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Michele
 */
public class ListaNoleggiati{
    private HashMap<String, Calendar> noleggiate;

    public ListaNoleggiati() {
        this.noleggiate = new HashMap<>();
    }

    public HashMap<String, Calendar> getNoleggiate() {
        return noleggiate;
    }
    
    public void addData(GregorianCalendar date, String targa){
        noleggiate.put(targa, date);
    }
    
    public void restituisci(String targa){
        noleggiate.remove(targa);
    }
    
    public long durataNoleggio(GregorianCalendar d, String targa){
        if(noleggiate.containsKey(targa)){
           return daysBetween(d, (GregorianCalendar) noleggiate.get(targa));
        }
        return 0;
    }
    
    private long daysBetween(GregorianCalendar startDate, GregorianCalendar endDate) {
    long end = endDate.getTimeInMillis();
    long start = startDate.getTimeInMillis();
    return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
    
    public boolean isNoleggiato(String targa){
        return noleggiate.containsKey(targa);
    }
    
    public Calendar getData(String targa){
        if(noleggiate.containsKey(targa))
            return noleggiate.get(targa);
        else
            return null;
    }
}
