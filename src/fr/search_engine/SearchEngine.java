package fr.search_engine;

import java.nio.file.Path;

public class SearchEngine {
	private Path indexation_directory;
	private IndexedPage[] pages;
	
	public SearchEngine(Path indexation_directory) {
		
	}
	
	public IndexedPage getPage(int i) {
		return pages[i];
	}
	
	private int getPagesNumber() {
		return pages.length;
	}
	
	public SearchResult[] launchRequest(String requestString) {
		return null;
	}
	
	public void printResults(String requestString){
		
	}
}
