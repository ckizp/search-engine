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
		String[] temp = text.split(" ");
		for (int i = 0; i < temp.length; i++) {
			words[i] = temp[i] + ":" + this.getCount(temp[i]);
		}
		
		for (int i = 0 ; i < words.length; i++) {
			for (int j = 0; j < temp.length; i++) {
				if (words[i] == temp[j])
					break;
				else if (i == (temp.length - 1))
					temp[i+1] = words[i];
			}
		}
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
    	
    	for (int i =0; i<words.length;i++) {
    		String[] list_tmp = words[i].split(":");
    		if (list_tmp[0].equals(word)) {
    			nombreOccurences = Integer.parseInt(list_tmp[1]);
    		}
    	}
    	return nombreOccurences;
    }
    
    
    
	public double getPonderation(String word) {
		return (1.0/this.getNorm())*this.getCount(word);
	}
	
	public double proximity(IndexedPage page) {
    	
		double sum = 0 ;
		double tmpSum = 0 ;
		
		for (int i =0; i<page.words.length;i++) {
			
			//System.out.println(page.words.length);
			//System.out.println(this.words.length);
			
			for(int j = 0 ;  j< this.words.length ; j ++ )
			{
				String[] list_tmp = this.words[i].split(":");
				String[] list_tmp2 = this.words[j].split(":");
				
				if(list_tmp2[0].equals(list_tmp[0]))
				{
					tmpSum = getPonderation(list_tmp2[0]) * getPonderation(list_tmp[0]) ;
					
					sum = sum + tmpSum ; 
					
				}
				
	
				
			}

		
    	}
		
		return sum ; 
		
		
		
	}
	
	public String toString() {
		return "";
	}
}
