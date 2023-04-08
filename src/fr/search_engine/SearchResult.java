package fr.search_engine;

public class SearchResult {
	private String url;
	private double score;

	public SearchResult(String url) {
		this.url = url;
		score = 0;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	public String getURL() {
		return url;
	}
	
	public double getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return "SearchResult [url=" + url + ", score=" + score + "]";
	}
}
