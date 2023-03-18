package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import search_engine.IndexedPage;
import search_engine.SearchEngine;
import search_engine.SearchResult;

class SearchEngineTest {

	private SearchEngine engine;
	private Path indexFolder;
	
	@BeforeEach
	void setUp() throws Exception {
		indexFolder = Paths.get(".\\\\src\\\\INDEX");
		engine = new SearchEngine(indexFolder);
	}

	@Test
	void testGetPage() {
		// Test for valid index
		if(engine.getPagesNumber() > 0) {
			IndexedPage page = engine.getPage(0);
			assertNotNull(page);
		}
		
		// Test for invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> engine.getPage(-1));
		if(engine.getPagesNumber() > 0) {
			assertThrows(IndexOutOfBoundsException.class, () -> engine.getPage(engine.getPagesNumber()));
		}
	}
	
	@Test
	void testLaunchRequest() {
		// Test pour une requête valide
		String requestString = "manger une pomme";
		SearchResult[] results = engine.launchRequest(requestString);
		assertNotNull(results);
		
		// Test pour une requête vide
		requestString = "";
		results = engine.launchRequest(requestString);
		assertEquals(0, results.length);
	}
	
	@Test
	void testPrintResults() {
		// Test pour une requête valide
		String requestString = "manger";
		engine.printResults(requestString);
		
		// Test pour une requête vide
		requestString = "";
		engine.printResults(requestString);
	}
	
	@Test
	void testMain() throws IOException, URISyntaxException {
	    // Test pour une requête valide en ligne de commande
	    String[] args = {"manger", "pomme"};
	    SearchEngine.main(args);

	    // Test pour une requête vide en ligne de commande
	    args = new String[0];
	    try {
	        SearchEngine.main(args);
	    } catch (IllegalArgumentException e) {
	        // Le tableau d'arguments est vide, ce qui est une erreur
	        System.out.println("Erreur : " + e.getMessage());
	    } catch (Exception e) {
	        // Une autre exception s'est produite
	        e.printStackTrace();
	    }
	}


}
