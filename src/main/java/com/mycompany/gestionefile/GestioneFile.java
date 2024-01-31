/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gestionefile;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Filippo Mandorlo
 */
public class GestioneFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //1)LETTURA
        Lettore lettore = new Lettore("user.json");
        lettore.start();
        try {
            lettore.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestioneFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        //2)ELABORAZIONE
        String username = null;
        String password = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Inserisci l'username");
            username = br.readLine().toUpperCase();  
            System.out.println("Inserisci la password");
             password = br.readLine().toUpperCase();
        } catch (IOException e) {
            System.err.println("Errore durante la lettura dell'input: " + e.getMessage());
        }
        
        // Istanzio la matrice di Vigenere
        ArrayList<Vigenere> quadranti = new ArrayList<Vigenere>();
        Matrice matrice = new Matrice("TPSIT");
        Vigenere quadrante_1=new Vigenere(0,12,0,12,matrice);
        quadranti.add(quadrante_1);
    
        Vigenere quadrante_2=new Vigenere(0,12,12,26,matrice);
        quadranti.add(quadrante_2);

       Vigenere quadrante_3=new Vigenere(12,26,0,12,matrice);
        quadranti.add(quadrante_3);

       Vigenere quadrante_4=new Vigenere(12,26,12,26,matrice);
       quadranti.add(quadrante_4);

        for(Vigenere v:quadranti){
         Thread t= new Thread(v);
         t.start();
         try {
           t.join();
         }catch (InterruptedException ex) {
             System.err.println("Errore metodo join");
         }
        
        
        
        //3) SCRITTURA
        Scrittore scrittore = new Scrittore("output.csv", username+";"+matrice.cifra(password));
        Thread threadScrittore = new Thread(scrittore);
        threadScrittore.start();
    }
   }  
}
