package test;

import static org.junit.Assert.*;
import org.junit.Test;

import search_engine.SearchResult;

public class SearchResultTest {

    @Test
    public void testConstructor() {
        SearchResult result = new SearchResult("https://fr.vikidia.org/wiki/");
        assertEquals("https://fr.vikidia.org/wiki/", result.getURL());
        assertEquals(0.0, result.getScore(), 0.0);
    }

    @Test
    public void testSetURL() {
        SearchResult result = new SearchResult("https://fr.vikidia.org/wiki/");
        result.setURL("https://fr.vikidia.org/wiki/Flan");
        assertEquals("https://fr.vikidia.org/wiki/Flan", result.getURL());
    }

    @Test
    public void testSetScore() {
        SearchResult result = new SearchResult("https://fr.vikidia.org/wiki/");
        result.setScore(5.0);
        assertEquals(5.0, result.getScore(), 0.0);
    }

    @Test
    public void testToString() {
        SearchResult result = new SearchResult("https://fr.vikidia.org/wiki/");
        String expected = "SearchResult [url=https://fr.vikidia.org/wiki/, score=0.0]";
        assertEquals(expected, result.toString());
    }
}
