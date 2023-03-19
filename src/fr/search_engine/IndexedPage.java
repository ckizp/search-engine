package search_engine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import tools.Lemmatizer;

public class IndexedPage {
	private String url;
	// Utilisation d'une HashMap car clé unique, si on tente d'entrer une valeur à une clé x et que x existe déjà, sa valeur sera écrasée par la nouvelle
	private HashMap<String, Integer> occurrences;
	
	public IndexedPage(String[] lines) throws IllegalArgumentException {
		int i = 0;
		occurrences = new HashMap<>();
		Lemmatizer lemmatizer = new Lemmatizer(".\\src\\lemmatisation\\");
	    if (lines == null || lines.length < 1) {
	        throw new IllegalArgumentException("The array in argument may not have been initialized or is empty.");
	    }
		url = lines[0];
		
		for (i = 1; i < lines.length; i++) {
			String[] link = lines[i].split(":");
			if (link.length != 2) {
	            throw new IllegalArgumentException("The element at index " + i+1 + " doesn't respect the following format: word:occurrence_number (" + lines[i] + ")");
	        }
			link[0] = lemmatizer.lemmatize(link[0]);
			if (link[0].equals(""))
				continue;
			try {
				occurrences.put(link[0], Integer.parseInt(link[1]));
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("The element at index " + i+1 + " doesn't respect the following format: word:occurrence_number (" + lines[1] + ")");
	        }
		}
	}
	
	public IndexedPage(Path path) throws IllegalArgumentException {
	    int i;
	    occurrences = new HashMap<>();
	    Lemmatizer lemmatizer = new Lemmatizer(".\\src\\lemmatisation\\");
	    try {
	        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
	        if (allLines.size() < 1)
	            throw new IllegalArgumentException("The document " + path.getFileName() + " is empty");
	        url = allLines.get(0);
	        for (i = 1; i < allLines.size(); i++) {
	            String[] link = allLines.get(i).split(":");
	            if (link.length != 2)
	                throw new IllegalArgumentException("La ligne n°" + i + " du document " + path.getFileName() + " ne respecte pas le format suivant: mot:nombre_d'occurrence");
	            //link[0] = lemmatizer.lemmatize(link[0]);
	            if (link[0].equals(""))
	                continue;
	            try {
	                occurrences.put(link[0], Integer.parseInt(link[1]));
	            } catch (NumberFormatException e) {
	                throw new IllegalArgumentException("La ligne n°" + i + " du document " + path.getFileName() + " ne respecte pas le format suivant: mot:nombre_d'occurrence");
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public IndexedPage(String text) {
		int i = 0;
		occurrences = new HashMap<>();
		Lemmatizer lemmatizer = new Lemmatizer(".\\src\\lemmatisation\\");
		text = text.toLowerCase();

		// On découpe le texte en mots qu'on stocke dans un table de type String[]
		String[] splitText = text.split(" ");
		

		for (String word : splitText) {
			word = lemmatizer.lemmatize(word);
			if (!occurrences.containsKey(word))
				occurrences.put(word, 1);
			else
				occurrences.put(word, occurrences.get(word) + 1);
		}
		
		for (String word : occurrences.keySet())
			System.out.println(word + ":" + occurrences.get(word));
	}
	
	public String getUrl() {
		return this.url;
	}
	
    public double getNorm() {
    	// Calcule et renvoie la norme du vecteur à partir du nombre d'occurrences de chaque mot
    	int sum = 0;
    	for (int count : occurrences.values())
    		sum += (int) Math.pow(count, 2);
    	return Math.sqrt((double) sum);
    }
	
    public int getCount(String word) {
    	// Renvoie le nombre d'occurrences du mot word dans le document
    	if (occurrences.containsKey(word))
    		return occurrences.get(word);
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
		for (String word : occurrences.keySet()) {
			if (document.getCount(word) > 0)
				sum += this.getCount(word) * document.getCount(word);
		}
		double norm1 = this.getNorm();
		double norm2 = document.getNorm();
		return (sum / (norm1 * norm2)) * 100;
	}
	
	public String toString() {
		return "IndexedPage [url=" + (url != null ? url : "non définie") + "]";
	}
}
