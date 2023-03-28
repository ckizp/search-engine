package tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import search_engine.SearchEngine;

public class Indexer {
	private Path bin_directory;
	private HashMap<String, List<String>> tags;
	
	public Indexer(Path bin_directory) {
		this.bin_directory = bin_directory;
		this.tags = new HashMap<String, List<String>>();
		Path tags_directory = bin_directory.resolve("indexation");
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(tags_directory, path -> Files.isRegularFile(path) && Files.isReadable(path))) {
			for (Path filePath : stream)
				tags.put(filePath.getFileName().toString().substring(0, 3), Files.readAllLines(filePath, StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du répertoire " + tags_directory.getFileName() + " : " + e.getMessage());
		}
	}
	
	public void index() {
		Path pages_directory = bin_directory.resolve("INDEX_FILES");
		int count = 0;
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(pages_directory, path -> Files.isRegularFile(path) && Files.isReadable(path))) {
			for (Path filePath : stream) {
				String name = new String();
				name = count + "-";
				List<String> allLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
				
				for(String key : tags.keySet()) {
					for(String word : allLines) {
						String[] split = word.split(":");
						if (split.length != 2) continue;
						if (tags.get(key).contains(split[0])) {
							name += key + "-";
							break;
						}
					}
					count++;
				}
				Files.move(filePath, filePath.resolveSibling(name + ".txt"));
			}
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du répertoire " + pages_directory.getFileName() + " : " + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws URISyntaxException {
		URL location = SearchEngine.class.getProtectionDomain().getCodeSource().getLocation();
	    Path binFolder = Paths.get(location.toURI());
		Indexer indexer = new Indexer(binFolder);
		indexer.index();
	}
}
