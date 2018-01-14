package org.wic.wsd;
//import org.wic.wsd;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.wic.wsd.corpus.Corpus;
import org.wic.wsd.corpus.Text;
import org.wic.wsd.dictionary.Dictionary;

public class Main2 {
	
	public int tailleDic(Dictionary d) {
		return d.size();
	}
	
	public int nombreDeSens(String mot, Dictionary d) {
		return d.getSenses(mot).size();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Main2 main = new Main2();
		
		Dictionary d1 = new Dictionary();
		String path = "/home/dcissm2/bolshakd/Documents/TP-WSD-WIK/Dictionnaires-Lesk/Dict-Lesk.xml";
		d1.loadDictionary(path);
		
		// System.out.println("La taille de d1: " + main.tailleDic(d1));
		
		System.out.println("Nombre de sens de mot cat: " + main.nombreDeSens("cat",d1));
		
		
		
	}

}
