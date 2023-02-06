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
	
	/* Function from the Getters & Setters
	 * -> setURL : it's ...
	 * -> setScore : it's ...
	 * -> getURL : it's ...
	 * -> getScore : ...
	 */
	
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
		return this.url;
	}
	
	// GETTER [Argument : 0, variable affected : Score]
	public double getScore() {
		return this.score;
	}
	
	// Function ToString 
	
	public String toString(){
		// On retourne L'url qui à eté demander par l'utilisateur
		// La requetes sera pris en Args
		return "IndexedFile ["+this.url+"]";
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
