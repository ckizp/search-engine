package fr.search_engine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SearchEngine {
	private Path indexationDirectory;
	private List<IndexedPage> pages;
	
	public SearchEngine(Path indexationDirectory) {
		this.indexationDirectory = indexationDirectory;
		pages = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		try {
            Files.walkFileTree(this.indexationDirectory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path filePath, BasicFileAttributes attributes) {
                    executorService.submit(() -> {
                    	pages.add(new IndexedPage(filePath));
                    });
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
            	executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
            	System.err.println("Thread execution interrupted.");
            }
            System.out.println("Temps de chargement du moteur de recherche : " + String.valueOf(System.currentTimeMillis()-startTime) + "ms.");
        }
	}
	
	public IndexedPage getPage(int i) throws IndexOutOfBoundsException {
		Objects.requireNonNull(pages);
	    if (pages.isEmpty()) {
	        throw new IndexOutOfBoundsException("The array in argument (pages) is empty.");
	    }
	    if (i < 0 || i >= pages.size()) {
	        throw new IndexOutOfBoundsException("Unable to access document at index " + i + ". Number of documents : " + this.getPagesNumber());
	    }
	    return pages.get(i);
	}

	public int getPagesNumber() {
		return pages.size();
	}
	
	public SearchResult[] launchRequest(String requestString) {
		int i = 0;
		SearchResult[] results = new SearchResult[pages.size()];
		IndexedPage request = new IndexedPage(requestString);
		for (IndexedPage page : pages) {
			results[i] = new SearchResult(page.getUrl());
			results[i].setScore(request.proximity(page));
			i++;
		}
		return results;
	}
	
	public void printResults(String requestString) {
		int i;
		final int printLimit = 15;
		SearchResult[] results = launchRequest(requestString);
		
		// Applique un filtre au tableau results afin d'enlever tous les résultats dont le score est nul
		results = Arrays.stream(results).filter(result -> result.getScore() > 0.).toArray(SearchResult[]::new);
		// Filtre les r"sultats selon leur pertinence
	    Arrays.sort(results, Comparator.comparingDouble(SearchResult::getScore).reversed());
	 
	    // Affichage des résultats les plus pertinents (limite fixée par printLimit)
	    if (results.length == 0)
	    	System.out.println("Aucun résultat pour cette recherche !");
	    else
	    	System.out.println("Voici ce que nous avons trouvé :");
		for (i = 0; i < ((results.length < printLimit) ? (results.length) : (printLimit)); i++)
			System.out.println(i + " - " + results[i].toString());
		System.out.print("\n");
	}
	
	public static void main(String[] args) throws URISyntaxException, IOException {
	    Scanner scanner = new Scanner(System.in);
	    String request;
	    Path indexFolder = Paths.get(System.getProperty("user.dir") + "/src/INDEX_FILES");
	    SearchEngine engine = new SearchEngine(indexFolder);

	    if (args.length > 0) {
	        request = String.join(" ", args);
	        engine.printResults(request);
	    } else {
	        do {
	            System.out.println("Lancez une recherche (tapez 'exit' pour quitter le moteur) :");
	            request = scanner.nextLine();
	            if (request.isEmpty()) {
	                throw new IllegalArgumentException("La requête ne peut pas être vide !");
	            }
	            engine.printResults(request);
	        } while (!request.equals("exit"));
	    }
	    scanner.close();
	}

}
