package fr.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Checker {
	private List<String> references;
	
	public Checker() {
		references = new ArrayList<>();
		Path referencesPath = Paths.get(System.getProperty("user.dir") + "/src/").resolve("references.txt");
		try {
			references = Files.readAllLines(referencesPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String check(String text) {
		String[] words = text.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			words[i] = this.search(words[i]);
		}
		return String.join(" ", words);
	}
	
	public String search(String entry) {
		int minDistance = Integer.MAX_VALUE;
		String result = entry;
		for (String reference : references) {
			int distance = levenshteinDistance(entry, reference);
			if (distance < minDistance && reference.charAt(0) == entry.charAt(0)) {
				minDistance = distance;
				result = reference;
			}
		}
		return result;
	}
	
	private static int levenshteinDistance(String word, String reference) {
		int[][] matrix = new int[reference.length() + 1][word.length() + 1];

		for (int i = 0; i <= reference.length(); i++) {
		    matrix[i][0] = i;
		}
		for (int j = 0; j <= word.length(); j++) {
		    matrix[0][j] = j;
		}

		for (int i = 1; i <= reference.length(); i++) {
		    for (int j = 1; j <= word.length(); j++) {
		        if (reference.charAt(i - 1) == word.charAt(j - 1)) {
		            matrix[i][j] = matrix[i - 1][j - 1];
		        } else {
		            matrix[i][j] = Math.min(
		                matrix[i - 1][j - 1] + 1,
		                Math.min(matrix[i][j - 1] + 1,
		                matrix[i - 1][j] + 1)
		            );
		        }
		    }
		}
		return matrix[reference.length()][word.length()];
	}
}
