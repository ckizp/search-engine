package fr.search_engine;

public class IndexedPage {
	private String url = null;
	private String[] words;
	private int[] counts;
	
	public IndexedPage(String[] lines) {
		if (lines[0] != null) {
			for(int i = 1; i < lines.length; i++) {
				words[i] = lines[i];
			}
		}
	}
	
	public IndexedPage(String text) {
		String[] temp = text.split(" ");
		for (int i = 0; i < temp.length; i++) {
			words[i] = temp[i] + ":" + this.getCount(temp[i]);
		}
		
		temp = null;
		for (int i = 0 ; i < words.length; i++) {
			for (int j = 0; j < temp.length; i++) {
				if (words[i] == temp[j])
					break;
				else if (i == (temp.length - 1))
					temp[i+1] = words[i];
			}
		}
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public double getNorm() {
		return 0.;
	}
	
	public int getCount(String word) {
		return 0;
	}
	
	public double getPonderation(String word) {
		return 0.;
	}
	
	public double proximity(IndexedPage page) {
		return 0.;
	}
	
	public String toString() {
		return "";
	}
}
