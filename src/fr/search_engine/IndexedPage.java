package fr.search_engine;

import java.util.Arrays;

public class IndexedPage {
	

	private String url ;
	private String[] words;
//	private int[] counts;
	
	public IndexedPage(String[] lines) {
		
		this.url = lines[0];
		this.words = Arrays.copyOfRange(lines, 1, lines.length);
	}
	
	public IndexedPage(String text) {
		ArrayList<String> str = new ArrayList<>();
		text.toLowerCase();		
		words = text.split(" ");
		Arrays.sort(words);
		
		str.add(words[0] + ":" + this.getCount(words[0]));
		for (int i = 1; i < words.length; i++) {
			if (!words[i-1].equals(words[i])) {
				str.add(words[i] + ":" + this.getCount(words[i]));
			}
		}
		words = str.toArray(new String[0]);
	}
	
	public String getUrl() {
		return this.url;
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

	
    public int getCount(String word) {
    	/*Renvoie le nombre d'occurences du mot*/
    	int nombreOccurences = 0;
    	
    	for (int i = 0; i < this.words.length; i++) {
    		//String[] list_tmp = this.words[i].split(":");
    		if (words[i].equals(word)) {
    			nombreOccurences++;
    		}
    	}
    	return nombreOccurences;
    }
    
    
    
	public double getPonderation(String word) {
		/* Mathématique :
		Par exemple Hello en arg : 10 
		La norme = 11
		Alors on fait : 1/11 * 10 
		= 
		*/
		return (1.0/this.getNorm())*this.getCount(word);
	}
	
	public double proximity(IndexedPage page) {
		return 0.0;
	}
	
	public String toString() {
		return "";
	}
}
