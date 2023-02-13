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
	
    public double getNorm() {
    	/*Renvoie la norme du nombre d'occurences des mots
    	 *donnés en parametre du constructeur*/

    	double norme = 0;
    	
    	for (int i = 0 ; i < words.length ; i++) {
    		String[] list_tmp = words[i].split(":");
    		norme = norme + (double) Math.pow(Integer.parseInt(list_tmp[1]), 2);
    	}
    	return (double) Math.sqrt(norme);
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
		
		double prod_scalaire = 0 ;
		double tmpProd = 0 ;
		
		double norme1 = this.getNorm();
		double norme2 = page.getNorm();
		
		for (int i = 0; i<page.words.length;i++) {		
			for(int j = 0 ;  j< this.words.length ; j++ ) {
				String[] list_tmp = this.words[i].split(":");
				String[] list_tmp2 = page.words[j].split(":");
				
				if(list_tmp2[0].equals(list_tmp[0])){	
					tmpProd = Double.parseDouble(list_tmp2[1]) * Double.parseDouble(list_tmp[1]) ;
					prod_scalaire = prod_scalaire + tmpProd ; 
				}
			}
		}
		double tmp = norme1 * norme2;
		double cosinus = prod_scalaire/tmp; 
		
		return cosinus ; 
	}
	
	public String toString() {
		return "";
	}
}
