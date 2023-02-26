package fr.search_engine;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.StreamSupport;

public class SearchEngine {
	private Path indexation_directory;
	private IndexedPage[] pages;
	
	public SearchEngine(Path indexation_directory) {
		int i = 0;
		this.indexation_directory = indexation_directory;
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(indexation_directory, path -> Files.isRegularFile(path) && path.toString().endsWith(".txt"));
			DirectoryStream<Path> temp = Files.newDirectoryStream(indexation_directory, path -> Files.isRegularFile(path) && path.toString().endsWith(".txt"));
			pages = new IndexedPage[Math.toIntExact(StreamSupport.stream(stream.spliterator(), false).count())];
			for (Path filePath : temp) {
				pages[i] = new IndexedPage(filePath);
				i++;
			}
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du répertoire " + indexation_directory.getFileName() + " : " + e.getMessage());
		}
	}
	
	public IndexedPage getPage(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= pages.length)
			throw new IndexOutOfBoundsException("Impossible d'accéder au document à l'index " + i + ". Nombre de documents : " + this.getPagesNumber());
		return pages[i];
	}
	
	private int getPagesNumber() {
		return pages.length;
	}
	
	public SearchResult[] launchRequest(String requestString) {
		int i = 0;
		SearchResult[] results = new SearchResult[pages.length];
		IndexedPage request = new IndexedPage(requestString);
		for (IndexedPage page : pages) {
			results[i] = new SearchResult(page.getUrl());
			results[i].setScore(request.proximity(page));
			i++;
		}
		return results;
	}
	
	public void printResults(String requestString){
		final int printLimit = 15;
		int i;
		SearchResult[] results = launchRequest(requestString);
		/*
		int i, k, imax;
		SearchResult temp;
		for (k = results.length-1; k >= 1; k--) {
			imax = 0;
			for (i = 1; i <= k; i++) {
				if (results[imax].getScore() > results[i].getScore())
					imax = i;
			}
			temp = results[k];
			results[k] = results[imax];
			results[imax] = temp;
		}*/
		
		// Applique un filtre au tableau results afin d'enlever tous les résultats dont le score est nul
		results = Arrays.stream(results).filter(result -> result.getScore() != 0.0).toArray(SearchResult[]::new);
		// Filtre les résultats selon leur pertinence
	    Arrays.sort(results, Comparator.comparingDouble(SearchResult::getScore).reversed());
	 
	    // Affichage des résultats les plus pertinents (limite fixée par printLimit)
		for (i = 0; i < ((results.length < printLimit) ? (results.length) : (printLimit)); i++) {
			System.out.println(i + " - " + results[i].toString());
		}
	}
}
