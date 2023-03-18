package fr.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Extractor {
	private Path path;
	private Stream<String> stream;
	
	public Extractor (Path path) {
		this.path = path;
	}
	
	public void readOnStrearm () throws IOException {
		try {
			stream = Files.lines(path);
		} catch (IOException e) {
			throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
		}
	}
	
	public void extract(char letter) throws IOException {
		try {
			this.readOnStrearm();
			FileWriter writer = new FileWriter(".\\src\\lemmatisation\\"+letter+".txt", Charset.forName("UTF-8"));
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
	
	public static void main(String[] args) {
		Extractor extractor = new Extractor(Paths.get(".\\src\\lemmatisation\\french_dictionary.txt"));
		for (char c = 'a'; c <= 'z'; c++) {
			try {
				extractor.extract(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
