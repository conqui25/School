/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veicoli;

/**
 *
 * @author 75662330
 */
public class FileException extends Exception{
    private String matter="";

    public FileException(String matter) {
        this.matter = matter;
    }

    public String getMatter() {
        return matter;
    }
    
    
    
}
