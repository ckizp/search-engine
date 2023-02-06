package fr.search_engine_tests;

import fr.search_engine.IndexedPage;

public class SearchEngineTests {
	
	public static void main(String[] args) { 
	String[] lines = {"http://example.org", "hello:1", "world:1", "java:7"};
	IndexedPage indexedPageTest = new IndexedPage(lines);
	
	System.out.println(indexedPageTest.getNorm());
	}
}
