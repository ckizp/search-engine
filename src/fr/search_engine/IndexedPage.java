package fr.search_engine;

import java.util.Arrays;
import java.util.ArrayList;

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
	
			/**
		ArrayList<String> str = new ArrayList<>();
		String[] tempWords = new String[words.length+1];
		
		if(words[0]!=null) {
			tempWords[0]=words[0];
		}
		
		for (int i = 1 ; i < words.length; i++) {	
			if (!words[i-1].equals(words[i])) {
				tempWords[i]=words[i];
			}
		}
		
		
		this.words = Arrays.copyOf(tempWords,tempWords.length-compteur-1);
		
		counts = new int[tempWords.length];
		Arrays.fill(counts,1);
		
	
		System.out.println("\nwords : ");
		for(int i = 0; i < words.length ; i++) {
			System.out.println(words[i]+" ");
		}
		System.out.println("\ntemp : ");
		for(int i = 0; i < words.length ; i++) {
			System.out.println(tempWords[i]+" ");
		}
		
		for (int i = 1 ; i < words.length; i++) {
			for (int j = 0 ; j < words.length ; j++) {
				if (tempWords[i].equals(words[j])) {
					counts[i]+=1;
				}
			}
		}
		**/
	
	
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
    	for (int i = 0; i < words.length; i++) {
    		if (words[i].split(":")[0].equals(word))
    			return Integer.parseInt(words[i].split(":")[1]);
    	}
    	return 0;
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
				
				if(this.count[j].equals(page.count[i])){	
					tmpProd =this.count[j] * page.count[i] ;
					prod_scalaire = prod_scalaire + tmpProd ; 
				}
			}
		}
		double tmp = norme1 * norme2;
		double cosinus = prod_scalaire/tmp; 
		
		return cosinus ; 
	}
	
	public String toString() {
		if (url != null)
			return "IndexedPage [url=" + url + "]";
		else
			return "IndexedPage [words=" + Arrays.toString(words);
	}
}
