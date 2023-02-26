package fr.search_engine;

public class SearchResult {
	// VARIABLES
	private String url;
	private double score;
	
	/* Only one Function from the constructeur
	 * SearchResult : This can be reused on other functions 
	 */
	
	// CONSTRUCTEUR 
	public SearchResult(String url) {
		this.url = url;
		score = 0;
	}
	
	// SETTER [Argument: 1, variable affected : url]
	public void setURL(String url) {
		this.url = url;
	}
	
	// SETTER [Arguments :1, variable affected : Score]
	public void setScore(double score) {
		this.score = score;
	}
	// GETTER [Argument : 0, variable affected : url]
	public String getURL() {
		return url;
	}
	
	// GETTER [Argument : 0, variable affected : Score]
	public double getScore() {
		return score;
	}
	
	// Function ToString 
	@Override
	public String toString() {
		return "SearchResult [url=" + url + ", score=" + score + "]";
	}

	// Programme Non Terminer, veuillez laissser un peu de temps 
	public static void main(String[] args) {
		SearchResult page1 = new SearchResult(args[0]);
		System.out.println("Degre de similarité : 0 ");
		page1.toString();
		/* Le programme main doit etre remplacer par :
		 * 
		 * IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org", "hello:10", "world:5"});
		 *  System.out.println(page1);
		 *  System.out.println(page1.getPonderation("hello")); 
		 *  System.out.println(page1.getPonderation("other"));
		 *  IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
		 *  System.out.println("Degre de similarite des deux pages: " + page1.proximity(page2));
		 *  
		 * */
	}
}
