/*
 * @author : akira
 * date : 06/02/2023 
 */

package fr.search_engine_tests;

/* Package Source : fr.search_engine_test 
 * IndexedPage.java
 * SearchEngine.java
 * SearchResult.java
 */ 

import fr.search_engine.IndexedPage;
import fr.search_engine.SearchResult; 
// Import never used  :   !! We have to do the modif 
import fr.search_engine.SearchEngine;

public class SearchEngineTests {
	
	public static void main(String[] args) {
		// Test for user : @akirasanthakumaran || File Name : SearchResult.java : 
		SearchResult page1 = new SearchResult(args[0]);
		System.out.println("Degre de similaritÃ© : 0 ");
		page1.toString();
		
		// Test for user : @thomasbres || File Name : IndexedPage.java
		String[] lines = {"http://example.org", "hello:10", "world:1", "java:7"};
		IndexedPage indexedPageTest = new IndexedPage(lines);
		System.out.println(indexedPageTest.getNorm());
		
		// Test for user : @akirasanthakumaran & @thomasbres || File Name : IndexedPage.java
		System.out.println(page1.getPonderation("hello")); // For function getPonderation 
		
		// Final Main :
		/*public static void main(String[] args) {
			  IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org", "hello:10", "world:5"});
			  System.out.println(page1);
			  System.out.println(page1.getPonderation("hello")); 
			  System.out.println(page1.getPonderation("other"));
			  IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
			  System.out.println("Degre de similarite des deux pages: " + page1.proximity(page2));
		}*/
		
	}

}

