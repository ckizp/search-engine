package fr.search_engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.tools.Lemmatizer;

public class IndexedPage {
	private String url;
	private String[] words;
	private int[] counts;
	private HashMap<String, Integer> occurrences;
	
	public IndexedPage(String[] lines) throws IllegalArgumentException {
		int i = 0;
		occurrences = new HashMap<>();
		Lemmatizer lemmatizer = new Lemmatizer(".\\\\src\\\\lemmatisation\\\\");
	    if (lines == null || lines.length < 1) {
	        throw new IllegalArgumentException("The array in argument may not have been initialized or is empty.");
	    }
	    
		url = lines[0];
		
		HashMap<String, Integer> links = new HashMap<>();
		
		for (i = 1; i < lines.length; i++) {
			String[] link = lines[i].split(":");
			if (link.length != 2) {
	            throw new IllegalArgumentException("The element at index " + i+1 + " doesn't respect the following format: word:occurrence_number (" + lines[i] + ")");
	        }
			
			link[0] = lemmatizer.lemmatize(link[0]);
			occurrences.put(link[0], 0);
			try {
				links.put(link[0], links.get(link[0]) + Integer.parseInt(link[1]));
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("The element at index " + i+1 + " doesn't respect the following format: word:occurrence_number (" + lines[1] + ")");
	        }
		}
		
		words = new String[links.size()];
		counts = new int[links.size()];
		
		i = 0;
		for (String word : links.keySet()) {
			words[i] = word;
			counts[i] = links.get(word);
			i++;
		}
		
		for (i = 0; i < words.length; i++) {
			System.out.println(words[i] + ":" + counts[i]);
		}
	}
	
	public IndexedPage(Path path) throws IllegalArgumentException {
		int i = 0;
		Lemmatizer lemmatizer = new Lemmatizer(".\\\\src\\\\lemmatisation\\\\");
		try {
			List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
			if (allLines.size() < 1) {
				throw new IllegalArgumentException("The document " + path.getFileName() + " is empty");
			}
			url = allLines.get(0);
			
			words = new String[allLines.size()-1];
			counts = new int[allLines.size()-1];
			
			HashMap<String, Integer> links = new HashMap<>();
			
			for (i = 1; i < allLines.size(); i++) {
				String[] link = allLines.get(i).split(":");
				if (link.length != 2) {
					throw new IllegalArgumentException("La ligne n°" + i + " du document " + path.getFileName() + " ne respecte pas le format suivant: mot:nombre_d'occurrence");
				}
				
				links.put(link[0], 0);
				try {
					links.put(link[0], links.get(link[0]) + Integer.parseInt(link[1]));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("La ligne n°" + i + " du document " + path.getFileName() + " ne respecte pas le format suivant: mot:nombre_d'occurrence");
				}
			}
			i = 0;
			for (String word : links.keySet()) {
				words[i] = word;
				counts[i] = links.get(word);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public IndexedPage(String text) {
		int i = 0;

		text = text.toLowerCase();

		// On découpe le texte en mots qu'on stocke dans un table de type String[]
		String[] splitText = text.split(" ");
		
		// Utilisation d'une HashMap car écrase la valeur d'une clé si la clé existe déjà
		HashMap<String, Integer> links = new HashMap<>();
		for (String word : splitText) {
			if (blackListedWords.contains(word) || word.length() <= 2)
				continue;
			char firstLetter = word.charAt(0);
			if (!dictionary.containsKey(firstLetter)) 
				this.loadDictionary(firstLetter);
				
			if (dictionary.get(firstLetter).containsKey(word))
				word = dictionary.get(firstLetter).get(word);
			
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
		return (sum / (norm1 * norm2))*100;
	}
	
	public String toString() {
		return "IndexedPage [url=" + (url != null ? url : "non définie") + "]";
	}
}
