package test;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import search_engine.IndexedPage;

public class IndexedPageTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyArgument() {
        new IndexedPage(new String[]{});
    }

    @Test
    public void testConstructorWithStringArrayArgument() {
        String[] lines = {"http://example.com", "word1:2", "word2:4"};
        IndexedPage ip = new IndexedPage(lines);
        assertEquals("http://example.com", ip.getUrl());
        assertEquals(2, ip.getCount("word1"));
        assertEquals(4, ip.getCount("word2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidStringArrayArgument() {
        String[] lines = {"http://example.com", "word1:2", "word2:4", "word3"};
        new IndexedPage(lines);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidStringArrayArgument2() {
        String[] lines = {"http://example.com", "word1", "word2:4"};
        new IndexedPage(lines);
    }

    @Test
    public void testConstructorWithPathArgument() {
        Path path = Paths.get("correctPath.txt");
        IndexedPage ip = new IndexedPage(path);
        assertEquals("http://example.com", ip.getUrl());
        assertEquals(2, ip.getCount("word1"));
        assertEquals(4, ip.getCount("word2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidPathArgument() {
        Path path = Paths.get("invalid_path.txt");
        new IndexedPage(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyFile() {
        Path path = Paths.get("empty.txt");
        new IndexedPage(path);
    }

    @Test
    public void testConstructorWithStringArgument() {
        String text = "word1 word2 word1 word2 word2";
        IndexedPage ip = new IndexedPage(text);
        assertEquals(2, ip.getCount("word1"));
        assertEquals(3, ip.getCount("word2"));
    }

    @Test
    public void testGetUrl() {
        String[] lines = {"http://example.com", "word1:2", "word2:4"};
        IndexedPage ip = new IndexedPage(lines);
        assertEquals("http://example.com", ip.getUrl());
    }

    @Test
    public void testGetNorm() {
        String[] lines = {"http://example.com", "word1:2", "word2:4"};
        IndexedPage ip = new IndexedPage(lines);
        assertEquals(Math.sqrt(20), ip.getNorm(), 0.01);
    }

    @Test
    public void testGetCount() {
        String[] lines = {"http://example.com", "word1:2", "word2:4"};
        IndexedPage ip = new IndexedPage(lines);
        assertEquals(2, ip.getCount("word1"));
        assertEquals(4, ip.getCount("word2"));
        assertEquals(0, ip.getCount("word3"));
    }
}
