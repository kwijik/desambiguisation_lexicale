

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.login.Configuration;

import org.wic.wsd.corpus.Corpus;
import org.wic.wsd.corpus.Text;
import org.wic.wsd.dictionary.Dictionary;
import org.wic.wsd.dictionary.Sense;
import org.wic.wsd.dictionary.Word;
import org.wic.wsd.Stats;
import org.wic.wsd.Correlation;
import org.wic.wsd.ProblemConfiguration;


public class Main2 {
	
	//public int tailleDic(Dictionary d) {
		// return d.size();
	//}
	
	//public LinkedList<String> correlation(){
		
	//}
	
	public int nombreDeSens(String mot, Dictionary d) {
		return d.getSenses(mot).size();
	}
	
	public static long nbCombinaisons(String s, Dictionary d){
		String [] words = s.split("[ .,-?]");
		System.out.println(words.length);
		long res = 1;
		for( int i = 0; i < words.length; i++ ){
			String word = words[i];
			System.out.println("words: "+word);
			if( word.equals("")){
				System.out.println("continue works");
				continue;
			}
			res *= d.getSenses(word).size();
			
		}
		return res;	
	}
	
	public static ArrayList<String> algoEx(String s, Dictionary d){
		String [] words = s.split("[ .,-?]");
		System.out.println(words.length);
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < words.length; i++){
			String word = words[i];
			if( word.equals("")){
				continue;
			}
			ArrayList<Sense> senses = d.getSenses(word);
			for(Sense sense : senses){
				String def = sense.getDef().toString();
				res.add(def);
			}
		}
		return res;
	}
	

	/*
	 * return configuration with max score for each text 
	 * max score = 0
	 * fpr i=0 ... n do
	 *   modify sense for one of the words
	 *   calcule score of new config
	 *   if score > maxScore
	 *     save config
	 * 
	 */
	
	// n - total num of object
	// m : number of all configurations considered as best in each iteration
	// s - number of cycles of stabilisation
	// public static ProblemConfiguration algoEstimationDistribution(Text t, Dictionary d, int n, int m, int s)
	// 100 -> 55 have 1 sense, 15 
	// 3 senses 
	// value 0..1 : 0..0.55 -> 1 sense; 0.55..0.7 -> 2 sense; 0.7..1 -> 3 sense 
	//
	
	public static ProblemConfiguration algoEstimationDistribution(Text t, Dictionary d, int n, int m, int s){
		int stab = s;
		// int maxScore = -1;
		List <ProblemConfiguration> list = new ArrayList<>(n);
		for (int i=0; i<n; i++){
			ProblemConfiguration pc = new ProblemConfiguration(t.getLength(), true, t);
			pc.computeScore(t, d);
			list.add(pc);
		}
		
		list.sort((l,r)->(int)((r.getScore() - l.getScore())*1000));
		
		// optimization:
		list.subList(m,list.size()).clear();
		
		// ProblemConfiguration maxPc = new ProblemConfiguration(t.getLength(), true, t);
		// maxPc.computeScore(t, d);
		while(stab > 0){
			for(int x = 0; x < m; x++){
				ProblemConfiguration currentPc = list.get(x).getClone();
				currentPc.newMakeChange(t);
				currentPc.computeScore(t, d);
				if (list.get(x).getScore() < currentPc.getScore()){
					list.set(x, currentPc);
					stab = s;
				} else {
					stab++;
				}
			}
			
		}
		list.sort((l,r)->(int)((r.getScore() - l.getScore())*1000));

		return list.get(0);
	}

	
//	public int getSelectedSense(int i);
	
//	public void setSelectedSenseAt(int index, int senseNumber);
	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		Main2 main = new Main2();
	
		Dictionary d1 = new Dictionary();
		// String path = "../Dictionnaires-Lesk/Dict-Lesk.xml";
		String path = args[0];

		d1.loadDictionary(path);
		
		System.out.println("La taille de d1: " + d1.size());
		
		System.out.println("Nombre de sens de mot cone: " + d1.getSenses("cone").size());
		
		System.out.println("Nombre de sens de mot pine: " + d1.getSenses("pine").size());
		
		ArrayList<Sense> sensDePine = d1.getSenses("pine");
		
		for (Sense s : sensDePine) {
			System.out.println(s.getSenseID());
		}
		
		System.out.println("the biggest similairity: " + d1.getSimilarityMax("pine", "cone"));
		
		System.out.println("--------");
		
		Stats s = new Stats();
		double[] score1 = {1,4,8,12,2,5,4};
		double[] score2 = {4,2,1,6,6,10,4};
		
		System.out.println("Correlation entre les series: " + s.getPearsonCorrelation(score1, score2));
		
		// System.out.println(d1.getSenses("pine"));
		
		System.out.println("--------");

		
		
		System.out.println(Correlation.lectureFichier());
		
		System.out.println("--------");

		System.out.println("sum similairity: " + d1.getSimilarityAverage("pine", "cone"));
		
		System.out.println("number of combinations " + nbCombinaisons("test", d1));
		ArrayList<String> defs = algoEx("test", d1);
		System.out.println("------");
		for (String def : defs){
			System.out.println(def);
		}
		System.out.println("");

		
		// ------//
		
		
		
        }
		
	}

}
