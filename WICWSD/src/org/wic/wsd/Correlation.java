package org.wic.wsd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Correlation {
	
	 public static LinkedList<String[]> lectureFichier(){
	        LinkedList<String[]> liste= new LinkedList<String[]>();
	        InputStream ips=null;
	        InputStreamReader ipsr=null;
	        try {
	            String fichier= "../wordsim353/paires.csv";
	            //String dirCourant= System.getProperty("user.dir");
	            //System.out.println(dirCourant);
	            ips=new FileInputStream(fichier); 
	            ipsr=new InputStreamReader(ips);
	            BufferedReader br=new BufferedReader(ipsr);
	            
	            //On lit chaque mot et on les range dans les tableaux
	            String ligne=br.readLine();
	            while ((ligne=br.readLine())!=null){
	                String[] tab= ligne.split(",");
	               liste.add(tab);
	            }
	            br.close();         
	        } catch (IOException ex) {
	        } finally {
	            try { 

	                ips.close();
	                ipsr.close();
	            } catch (IOException ex) {
	                System.out.println("erreur");
	            }
	        }
	        return liste;
	    }

}
