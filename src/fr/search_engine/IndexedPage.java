package fr.search_engine;

import java.util.Arrays;
import java.util.HashMap;
import java.text.Normalizer;
import java.util.ArrayList;

public class IndexedPage {
	private String url;
	private String[] words;
	private int[] counts;
	
	public IndexedPage(String[] lines) {
		this.url = lines[0];
		
		words = new String[lines.length - 1];
		counts = new int[lines.length - 1];
		
		for (int i = 1; i < lines.length; i++) {
			String[] link = lines[i].split(":");
			words[i-1] = link[0];
			counts[i-1] = Integer.parseInt(link[1]);
		}
	}
	
	public IndexedPage(String text) {
		int i = 0;
		
		// On traite le texte afin d'enlever les ponctuations, les chiffres, les accents et remplacer les majuscules par des minuscules
		text = text.replaceAll("[^\\p{L} ]", "");
		text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		text = text.toLowerCase();
		
		// On découpe le texte en mots qu'on stocke dans un table de type String[]
		String[] splitText = text.split(" ");
		
		// Utilisation d'une HashMap car écrase la valeur d'une clé si la clé existe déjà
		HashMap<String, Integer> links = new HashMap<>();
		for (String word : splitText) {
			if (!links.containsKey(word))
				links.put(word, 1);
			else
				links.put(word, links.get(word) + 1);
		}
		
		words = new String[links.size()];
		counts = new int[links.size()];
		
		for (String word : links.keySet()) {
			words[i] = word;
			counts[i] = links.get(word);
			i++;
		}
	}
	
	public String getUrl() {
		return this.url;
	}
	
    public double getNorm() {
    	// Calcule et renvoie la norme du vecteur à partir du nombre d'occurrences de chaque mot
    	int sum = 0;
    	for (int count : counts)
    		sum += (int) Math.pow(count, 2);
    	return Math.sqrt((double) sum);
    }
	
    public int getCount(String word) {
    	// Renvoie le nombre d'occurrences du mot word dans le document
    	for (int i = 0; i < words.length; i++) {
    		if (words[i].equals(word))
    			return counts[i];
    	}
    	return 0;
    }
    
	public double getPonderation(String word) {
		// Renvoie la valeur de la coordonnée correspondant au mot word dans le vecteur normalisé correspondant au document indexé 
		// Mathématiquement, il s'agit du produit de l'inverse de la norme du vecteur à partir du nombre d'occurence de chaque mot
		// et du nombre d'occurence de ce mot
		return (1 / this.getNorm()) * this.getCount(word);
	}
	
	public double proximity(IndexedPage document) {
		// Calcule la similitude entre deux pages ; le produit scalaire du vecteur de la page et du vecteur de l'autre page
		int sum = 0;
		for (String word : words) {
			if (document.getCount(word) > 0)
				sum += this.getCount(word) * document.getCount(word);
		}
		
		double norm1 = this.getNorm();
		double norm2 = document.getNorm();
		return sum / (norm1 * norm2);
	}
	
	public String toString() {
		return "IndexedPage [url=" + (url != null ? url : "non définie") + "]";
	}
}
