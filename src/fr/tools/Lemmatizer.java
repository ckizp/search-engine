package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import search_engine.SearchEngine;

public class Lemmatizer {
	private HashMap<Character, Map<String, String>> dictionary;
	private List<String> blacklistedWords;
 	private Path binPath; 
 	
	public Lemmatizer() {
		dictionary = new HashMap<>();
		URL location = SearchEngine.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			binPath = Paths.get(location.toURI());
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
	    
		Path path = binPath.resolve("lemmatisation\\blacklist.txt");
		try {
			blacklistedWords = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasLoaded(char character) {
		return dictionary.containsKey(character);
	}
	
	public void load(char character) {
		character = this.normalize(character);

		Path path = binPath.resolve("lemmatisation\\" + character + ".txt");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			Map<String, String> map = reader.lines().map(line -> line.split(":")).filter(line -> line.length == 2).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
			
			dictionary.put(character, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String lemmatize(String word) {
		if (word.length() == 0 || blacklistedWords.contains(word.toLowerCase())) return "";
		char firstChar = this.normalize(word.charAt(0));
		if (!this.hasLoaded(firstChar)) {
			dictionary.put(firstChar, new HashMap<String, String>());
			this.load(firstChar);
		}
		if (!dictionary.get(firstChar).containsKey(word)) {
			return word;
		}
		return dictionary.get(firstChar).get(word);
	}	
	
	private char normalize(char character) {
		return Normalizer.normalize(Character.toString(character), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").charAt(0);
	}
	
	public static void main(String[] args) {
		Lemmatizer lemmatizer = new Lemmatizer();
		lemmatizer.load('p');
	}
}
