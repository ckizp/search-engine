package fr.search_engine;

import java.lang.Math;
import java.util.Arrays;

public class IndexedPage {
    String url;
    String[] words;

    public IndexedPage(String[] lines) {
        this.url = lines[0];
        this.words = Arrays.copyOfRange(lines, 1, lines.length);
    }
    public IndexedPage(String text) {

    }
    public String getUrl() {
        return this.url;
    }
    
    public int getCount(String word) {
    	/*Renvoie le nombre d'occurences du mot*/
    	
    	int nombreOccurences = 0;
    	
    	for (int i =0; i<words.length;i++) {
    		String[] list_tmp = words[i].split(":");
    		if (list_tmp[0].equals(word)) {
    			nombreOccurences = Integer.parseInt(list_tmp[1]);
    		}
    	}
    	return nombreOccurences;
    }
    
    public int getNorm() {
    	/*Renvoie la norme du nombre d'occurences des mots
    	 * donner en parametre du constructeur*/

    	int  norme = 0;
    	
    	for (int i=0; i<words.length;i++) {
    		String[] list_tmp = words[i].split(":");
    		norme = norme + (int) Math.pow(Integer.parseInt(list_tmp[1]), 2);
    	}
    	return (int) Math.sqrt(norme);
    }

}