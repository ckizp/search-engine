package fr.search_engine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchEngine {
	private Path indexationDirectory;
	private List<IndexedPage> pages;
	
	public SearchEngine(Path indexationDirectory) throws IOException {
		this.indexationDirectory = indexationDirectory;
		pages = new ArrayList<>();
		/*try (DirectoryStream<Path> stream = Files.newDirectoryStream(indexationDirectory, path -> Files.isRegularFile(path) && Files.isReadable(path))) {
			for (Path filePath : stream)
				pages.add(new IndexedPage(filePath));
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du répertoire " + indexationDirectory.getFileName() + " : " + e.getMessage());
		}*/
		
		ExecutorService executorService = Executors.newFixedThreadPool(10); // Nombre de threads simultanés
		try {
            Files.walkFileTree(indexationDirectory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                    executorService.submit(() -> {
                        try {
                            byte[] fileContent = Files.readAllBytes(filePath);
                            pages.add(new IndexedPage(filePath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
	}
	
	public IndexedPage getPage(int i) throws IndexOutOfBoundsException {
	    if (pages == null || pages.size() == 0) {
	        throw new IndexOutOfBoundsException("The pages array is null or empty.");
	    }
	    
	    if (i < 0 || i >= pages.size()) {
	        throw new IndexOutOfBoundsException("Impossible d'acc�der au document � l'index " + i + ". Nombre de documents : " + this.getPagesNumber());
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
	
	public void printResults(String requestString){
		int i;
		final int printLimit = 15;
		SearchResult[] results = launchRequest(requestString);
		
		// Applique un filtre au tableau results afin d'enlever tous les r�sultats dont le score est nul
		results = Arrays.stream(results).filter(result -> result.getScore() > 0.).toArray(SearchResult[]::new);
		// Filtre les r�sultats selon leur pertinence
	    Arrays.sort(results, Comparator.comparingDouble(SearchResult::getScore).reversed());
	 
	    // Affichage des r�sultats les plus pertinents (limite fix�e par printLimit)
	    if (results.length == 0)
	    	System.out.println("Aucun résultat pour cette recherche !");
	    else
	    	System.out.println("Voici ce que nous avons trouvé :");
		for (i = 0; i < ((results.length < printLimit) ? (results.length) : (printLimit)); i++)
			System.out.println(i + " - " + results[i].toString());
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
	            System.out.println("\nLancez une recherche :");
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
