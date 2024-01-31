/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionefile;
import java.io.FileReader;
import java.io.IOException;

/**@author Filippo Mandorlo
 */
public class Copiatore extends Thread {
    String nomeFileDaCopiare;
    String nomeFileFinale;
   
    
    public Copiatore(String nomeFileDaCopiare,String nomeFileFinale){
         this.nomeFileDaCopiare=nomeFileDaCopiare;
       this.nomeFileFinale=nomeFileFinale;
    }
    
     /**
     * istanzio uno StringBuilder che aggiunge ogni carattere letto
     */
    
    
    public String leggi(){   
        StringBuilder sb= new StringBuilder();
         FileReader fr;
        int i; 
        try { 
            //1) apro il file
            fr = new FileReader(nomeFileDaCopiare);
            //2) leggo carattere per carattere e lo stampo 
            while ((i=fr.read()) != -1)
                sb.append(((char) i));
            sb.append(("\n\r"));
            //3) chiudo il file
            fr.close();
        } catch (IOException ex) {
            System.err.println("Errore in lettura!");
        }
        return sb.toString();
    }
        
        
    /**
     * scrivo il contenuto del file copiato,
     * nel file passato come secondo argomento del costruttore
     */    
     
    
    public void copia(){
        Scrittore scrittore = new Scrittore(nomeFileFinale,this.leggi() );
        Thread threadScrittore = new Thread(scrittore);
        threadScrittore.start();
         try {
            threadScrittore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore nel metodo join()");
        } 
    }
    

    @Override
    public void run(){
        copia();
    }
}
