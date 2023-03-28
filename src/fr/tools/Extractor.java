package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import search_engine.SearchEngine;

public class Extractor {
	private Path path;
	private Stream<String> stream;
	
	public Extractor (Path path) {
		this.path = path;
	}
	
	public void readOnStrearm () throws IOException {
		try {
			stream = Files.lines(path.resolve("french_dictionary.txt"));
		} catch (IOException e) {
			throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
		}
	}
	
	public void extract(char letter) throws IOException {
		try {
			this.readOnStrearm();
			FileWriter writer = new FileWriter(path.resolve(letter + ".txt").toString(), Charset.forName("UTF-8"));
			String[] results = stream.filter(line -> line.charAt(0) == letter).toArray(String[]::new);
			for (String line : results) {
				writer.write(line + System.lineSeparator());
				
			}
			writer.close();
			this.stream.close();
		} catch (IOException e) {
			throw new IOException("Erreur lors de la l'écriture du fichier : " + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws URISyntaxException {
	    URL location = SearchEngine.class.getProtectionDomain().getCodeSource().getLocation();
	    Path binFolder = Paths.get(location.toURI());
	    Path path = binFolder.resolve("lemmatisation");
	    
		Extractor extractor = new Extractor(path);
		for (char c = 'a'; c <= 'z'; c++) {
			try {
				extractor.extract(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
