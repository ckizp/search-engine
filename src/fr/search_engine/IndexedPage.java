package fr.search_engine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.tools.Lemmatizer;

public class IndexedPage {
	private String url;
	private HashMap<String, Integer> occurrences;

	public IndexedPage(String[] lines) {
		Objects.requireNonNull(lines);
	    if (lines.length < 1)
	    	throw new IllegalArgumentException("The array in argument (lines) is empty.");
	    
		occurrences = new HashMap<>();
		url = lines[0];
		
		for (int i = 1; i < lines.length; i++) {
			String[] pair = lines[i].split(":");
			if (pair.length != 2) {
	            throw new IllegalArgumentException("The element at index [" + i + "] doesn't respect"
	            		+ " the following format: word:occurrence_number (" + lines[i] + ")");
			}
			try {
				occurrences.put(pair[0], Integer.parseInt(pair[1]));
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("The element at index [" + i + "] doesn't respect"
	            		+ " the following format: word:occurrence_number (" + lines[i] + ")");
	        }
		}
	}

	public IndexedPage(Path path) {
	    occurrences = new HashMap<>();

	    try {
	        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
	        if (allLines.size() < 1)
	            throw new IllegalArgumentException("The document " + path.getFileName() + " is empty");
	        url = allLines.get(0);
	        for (int i = 1; i < allLines.size(); i++) {
	            String[] link = allLines.get(i).split(":");
	            if (link.length != 2) {
	                throw new IllegalArgumentException("The line no°" + (i+1) + " of the document " + path.getFileName() + " doesn't respect"
	                		+ " the following format: word:occurrence_number (" + allLines.get(i) + ")");
	            }
	            try {
	                occurrences.put(link[0], Integer.parseInt(link[1]));
	            } catch (NumberFormatException e) {
	                throw new IllegalArgumentException("The line no°" + (i+1) + " of the document " + path.getFileName() + " doesn't respect"
	                		+ " the following format: word:occurrence_number (" + allLines.get(i) + ")");
	            }
	        }
	    } catch (IOException e) {
	    	throw new IllegalArgumentException("Error while reading file " + path.getFileName() + ": " + e.getMessage());
	    }
	}

	public IndexedPage(String text) {
		occurrences = new HashMap<>();
		Lemmatizer lemmatizer = new Lemmatizer();
		text = text.toLowerCase();
		String[] splitText = text.split("\\s+");

		for (String word : splitText) {
			word = lemmatizer.lemmatize(word);
			if (word.isEmpty())
				continue;
			occurrences.merge(word, 1, Integer::sum);
		}
	}

	public String getUrl() {
		return url;
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